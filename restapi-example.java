public void vget() {
        String url = "https://il-ilce-rest-api.herokuapp.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+ "/v1/cities", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("snow", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("data");

                    Log.d("snowP", "onResponse: " + jsonObject2.get("cities"));
                    Log.d("snowP", "onResponse: " + jsonObject2.getString("cities"));
                } catch (Exception e) {
                    Log.d("snow", "onResponse: " + e.getMessage().toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("snow", "onErrorResponse: " + error.getMessage().toString());
            }
        }) ;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);


    }

    public void vpost() {
        String url="https://il-ilce-rest-api.herokuapp.com/v1/cities";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "api/users", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("snow", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("snowP", "onResponse: " + jsonObject.getString("job"));
                    Log.d("snowP", "onResponse: " + jsonObject.getString("createdAt"));
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("snow", "onErrorResponse: " + error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "the dark");
                params.put("job", "snow1");
                return params;
            }};

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
