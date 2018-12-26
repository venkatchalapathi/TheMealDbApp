# TheMealDbApp-is an API it provides some different meals and its preparation steps
Project Based on Themealdb

# Libraries Used

## 1.Retrofit
Retrofit is a REST Client library (Helper Library) used in Android and Java to create an HTTP request and also to process the HTTP response 
from a REST API. It was created by Square, you can also use retrofit to receive data structures other than JSON

dependencies for retrofit:

implementation 'com.squareup.retrofit2:retrofit:2.5.0'

For more details goto this link https://square.github.io/retrofit/

## 2.Gson
Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.

There are a few open-source projects that can convert Java objects to JSON. However, most of them require that you place Java annotations in your classes; something that you can not do if you do not have access to the source-code. 
Most also do not fully support the use of Java Generics. Gson considers both of these as very important design goals.

* Provide simple toJson() and fromJson() methods to convert Java objects to JSON and vice-versa
* Allow pre-existing unmodifiable objects to be converted to and from JSON
- Extensive support of Java Generics
- Allow custom representations for objects
- Support arbitrarily complex objects (with deep inheritance hierarchies and extensive use of generic types)
### Download
Gradle:

dependencies {

  implementation 'com.google.code.gson:gson:2.8.5'
  
}

For more goto this link https://github.com/google/gson

## 3.Picasso and Glide

Libraries for displaying images from the webservice
Goto this link to integrate to your project.

For Picasso  https://github.com/square/picasso

For Glide   https://github.com/bumptech/glide

## 4.Room Persistence Library

The Room persistence library provides an abstraction layer over 
SQLite to allow for more robust database access while harnessing the full power of SQLite.

Goto this link for integrating room database  https://developer.android.com/topic/libraries/architecture/room
For codelabs link   https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

## 5.Google Admob

AdMob makes earning revenue easy with in-app ads, 
actionable insights, ... by directly integrating Google Analytics for Firebase with AdMob.

For adding admob go through this link https://developers.google.com/admob/android/quick-start

## 6.Firebase Authentication
Most apps need to know the identity of a user. Knowing a user's identity allows an app to securely save
user data in the cloud and provide the same personalized experience across all of the user's devices.

Go through this link https://firebase.google.com/docs/auth/

## 7.API
Here I used themealdb.com
An open source database of Recipes from around the world.
For documentation https://themealdb.com/api.php


# Start Developing our App

## Create login Screen as follows:

<img src="https://user-images.githubusercontent.com/46043313/50432604-0b3df700-08f9-11e9-8659-ede9021bced7.png" width="300" height="450"/> 
