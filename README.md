# WynnProxy Client
## How to build a proxy server?
* See the [WynnProxy Server](https://github.com/BakaBotTeam/wynnproxy-server) repository
## How to use google api reverse proxy?
* Create a cloudflare worker
* Copy the code below to the worker
```javascript
export default {
  async fetch(request, env, ctx) {
    let url = new URL(request.url);

    // Modify the hostname based on the path
    if (url.pathname.startsWith('/translate_a/') || url.pathname.startsWith('/translate_tts') || url.pathname.startsWith('/translate')) {
      url.hostname = "translate.googleapis.com";
    } else {
      url.hostname = "translate.google.com";
    }

    // Create a new request with the modified URL and original request options
    let new_request = new Request(url.toString(), {
      method: request.method,
      headers: request.headers,
      body: request.body,
      redirect: 'follow'
    });

    // Fetch the modified request
    let response = await fetch(new_request);

    // Create a new response to handle content
    let new_response = new Response(response.body, response);

    // Add CORS headers if necessary
    new_response.headers.set("Access-Control-Allow-Origin", "*");
    new_response.headers.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    new_response.headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");

    return new_response;
  }
};
```