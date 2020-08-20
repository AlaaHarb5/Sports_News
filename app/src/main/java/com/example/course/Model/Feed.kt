package com.example.course.Model

// Root myDeserializedClass = JsonConvert.DeserializeObject<Root>(myJsonResponse);
data class Feed  (val url:String,val title:String,val description:String,val author:String,val link:String,val image:String )


