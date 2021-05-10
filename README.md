# ShopifyChallenge-Fall2021

#### 1) Overview: 
This application exposes REST apis for uploading, searching, and downloading images.
It uses Spring boot and H2 for an in memory SQL database. 

Images are represented with the Image class that contains a name, id, isPublic (for determining if other users can access the image), username (for determining which user owns the image), a byte[] (for storing the actual image), and a list of tags that are used to describe the image for searching.  

Users are managed by a very very basic User class. The class just contains a username and password that is stored in the DB once created. When a user logs in, a token is generated and associated with that user and then returned with the response to be used to authenticate future requests. 

#### 2) How to Use: 
If you have Maven installed you can run "mvn spring-boot:run" in the root directory.  

The project can also be run by running " java -jar ./target/demo-0.0.1-SNAPSHOT.jar "  

There are sample Postman calls included in the root directory.


#### 3) APIS
##### Register: /users/register 
Used for registering a new user, format the request body as: 
{
    "username": "test",
    "password": "test123"
}

##### Login: /users/login
Used for loging in as a registered user, format the request body as: 
{
    "username": "test",
    "password": "test123"
}
The response will set the header field "Token" with a unique token that must be placed in the header of all other requests

##### Logout: /users/logout/user
Used for logging out as a user, format the request body as: 
{
    "username": "test",
    "password": "test123"
}

##### Upload: /images/upload
Used for uploading an image, request must contain a "Token" header from a previous log in request. The body uses form-data with the first field being "file" that contains your chosen image as a jpg. The other field is "model" that contains a json file following this template: 
{
	"name": "Test",
	"isPublic": true,
	"tags":[
		"test",
		"test2"
	]
}

##### Download: /images/download/id/{imageID}
Used for downloading an image that is either public or was uploaded by the current user. The header must contain a valid Token, body is empty. 

##### Search: /images/search/desc?tags={tags}
Used for searching for an image based of the tags given to the image when uploaded. The result is a list of image ids and names that match one or more of the prodived tags and are either public or uploaded by the current user.  Header must contain a valid Token, body is empty.


#### 4) Future Expansion
The most obvious expansion is upgrading the login/authentication as it is extremly basic and not at all ready for any sort of production use :). Additionally, adding the ability to search for an image based of the name or ID of another image can be added quite easily by querrying the image by name then looking for the tags. 

#### 5) Sources
I used this article https://medium.com/javarevisited/a-simple-user-authentication-api-made-with-spring-boot-4a7135ff1eca as insperation for my User class implementation 
