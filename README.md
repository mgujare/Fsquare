###This app demonstrates the how to use Foursquare API wrapper library for Android. I published this library on bintray repo. Library is in beta version. For now it returns Venue details given the location information. All the data models in the library map to Foursquare API data models closely. 

###Add below lines to app/build.gradle file to include Foursquare API wrapper library:

```
   repositories {
    maven {
        url 'https://omg.bintray.com/Foursquare-android/'
     }
   }	
   .
   .//Other configs
   .
   .
   dependencies {
     //Other dependencies.
     compile 'FSquare:fsquare:0.0.1'
   }
```

### API calls to get Venue info. 

```
   FourSquareAPIFactory fourSquareFactory = new FourSquareAPIFactory(CLIENT_ID, CLIENT_SECRET);
        FourSquareAPI fourSquareAPI = fourSquareFactory.createAPI();
        Map<String, String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        //Read more at https://developer.foursquare.com/overview/versioning.
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String today = formatter.format(date);
        params.put("v", today);
        params.put("m", "foursquare");
        Call<SearchResponse> call = fourSquareAPI.searchVenue("40.7,-74", params);//Lat,long.
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    displayVenueData(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
```   
