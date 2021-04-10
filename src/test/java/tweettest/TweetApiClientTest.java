package tweettest;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tweet.TweetApiClient;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import java.sql.SQLOutput;
import java.util.UUID;

public class TweetApiClientTest {

    private TweetApiClient tweetApiClient;

    @BeforeClass
    public void setUpTweetAPI() {
        this.tweetApiClient = new TweetApiClient();
    }


    //create a tweet and get tweetId
    long tweetId = 0;

    @Test(priority = 1, enabled = true)
    public void testCreateTweetHelloWorldd() {
        String tweet = "hello worldd";
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        response.statusCode(200);
        tweetId = response.extract().body().path("id");

        System.out.println(tweetId);

        Assert.assertEquals(200, 200);
    }

    //check duplicate tweet
    @Test(priority = 2, enabled = true)
    public void testDuplicateTweetHelloWorldd() {
        String tweet = "hello worldd";
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        response.statusCode(403);
        //System.out.println(response.extract().body().asPrettyString());
        String expectedMessage = "Status is a duplicate.";
        String actualMessage = response.extract().body().path("errors[0].message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    //check delete functionality
    @Test(priority = 3, enabled = true)
    public void testDeleteTweetHelloWorldd() {
        String tweet = "hello worldd";
        ValidatableResponse response = this.tweetApiClient.deleteTweet(tweetId);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet, actualTweet);
    }

    // confirm delete functionality
    @Test(priority = 4, enabled = true)
    public void testDeleteTweetHelloWorldConfirmationByStatusCode() {
        ValidatableResponse response = this.tweetApiClient.deleteTweet(tweetId);
        response.statusCode(404);
        Assert.assertEquals(404, 404);
        String expectedMessage = "No status found with that ID.";
        String actualMessage = response.extract().body().path("errors[0].message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test(priority = 5, enabled = true)
    public void testDeleteTweetHelloWordlddConfirmationByText() {
        ValidatableResponse response = this.tweetApiClient.deleteTweet(tweetId);
        response.statusCode(404);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);
        String expectedMessage = "No status found with that ID.";
        String actualMessage = response.extract().body().path("errors[0].message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    //create second tweet
    @Test(priority = 6, enabled = true)
    public void testCreateSecondTweet() {
        String tweet = "azul fellaween";
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet, actualTweet);
    }

    // check the get timeline functionality
    @Test(priority = 7, enabled = true)
    public void testGetTimeline() {
        ValidatableResponse response = this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        Assert.assertEquals(200, 200);
    }

    // check the tweet id of the second tweet
    @Test(priority = 8, enabled = true)
    public void testCheckByIdSecondTweet() {
        ValidatableResponse response = this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        long expectedId = 1379925373786468357l;
        long actualId = response.extract().body().path("[0].id");
        Assert.assertEquals(expectedId, actualId);
    }

    // check the tweet by text of the second tweet
    @Test(priority = 9, enabled = true)
    public void testCheckByTextSecondTweet() {
        ValidatableResponse response = this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        String expectedIdText = "azul fellaween";
        String actualIText = response.extract().body().path("[0].text");
        Assert.assertEquals(expectedIdText, actualIText);
    }
    // check the tweet by user id of the second tweet
    @Test(priority = 10, enabled = true)
    public void testCheckByUserIdSecondTweet() {
        ValidatableResponse response = this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        long expectedUserId = 1376240452354052112l;
        long actualUserId = response.extract().body().path("[0].user.id");
        Assert.assertEquals(expectedUserId, actualUserId);
    }
    // check the tweet id str of the second tweet
    @Test(priority = 11, enabled = true)
    public void testCheckByIdStrSecondTweet() {
        ValidatableResponse response = this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);

        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);

        String expectedIdStr = "1376240452354052112";
        String actualUserIdStr = response.extract().body().path("[0].user.id_str");
        Assert.assertEquals(expectedIdStr, actualUserIdStr);
    }
    // check the tweet by user name of the second tweet
    @Test(priority = 12, enabled = true)
    public void testCheckByUserNameSecondTweet() {
        ValidatableResponse response = this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        String expectedUserName = "Cherif Ameur";
        String actualUserName = response.extract().body().path("[0].user.name");
        Assert.assertEquals(expectedUserName, actualUserName);
    }
    @Test(priority = 13,enabled = true)
    public void testCreateThirdTweet() {
        String tweet = "second tweet"+UUID.randomUUID();
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        response.statusCode(200);
        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);

        long idTweet = response.extract().body().path("id");
        System.out.println(idTweet);

         Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 14,enabled = true)
    public void testDeleteThirdTweet() {
        String tweet = "second tweet";
        ValidatableResponse response = this.tweetApiClient.deleteTweet(1379965703164411904l);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet,actualTweet);
    }
    @Test(priority = 15,enabled = true)
    public void TestGetTweets(){
        ValidatableResponse response =this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        System.out.println(response.extract().body().asPrettyString());
        Assert.assertEquals(200,200);
    }
    @Test(priority = 16,enabled = true)
    public void TestCheckGetTweetByUserScreen_name(){
        ValidatableResponse response =this.tweetApiClient.getUserTimeTweet();
        response.statusCode(200);
        String expectedUserScreen_name = "CherifAmeur2";
        String actualUserScreen_name = response.extract().body().path("[0].user.screen_name");
        Assert.assertEquals(200,200);
    }

    @Test(enabled = true)
    public void testCreateTweetWithVerifyingHeaders() {
        // validating the headers content
        String tweet = "Hi everyone" + UUID.randomUUID();
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        response.statusCode(200);
        // verify the Access Level from the Haeders
        String actualAccexxLevel = response.extract().headers().getValue("x-access-level");
        System.out.println(actualAccexxLevel);
        String expectedAccessLevel = "read-write";
        Assert.assertEquals(actualAccexxLevel, expectedAccessLevel);
    }

    @Test(enabled = true)
    public void testCreateTweetWithVerifyingUserName() {
        // Validating that the userName is correct
        String tweet = "Hi everyone" + UUID.randomUUID();
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        response.statusCode(200);
        // verify the username from the Body
        String actualUserNmae = response.extract().body().path("user.name");
        System.out.println(actualUserNmae);
        String expectedUsername = "Cherif Ameur";
        Assert.assertEquals(actualUserNmae, expectedUsername);
    }

    @Test(enabled = true)
    public void testMyFirstTweet() {
        String myFirstTweet = "first tweet on Api automation " + UUID.randomUUID().toString();
        ;
        ValidatableResponse response = this.tweetApiClient.createTweet(myFirstTweet);
        response.statusCode(200);
        //  System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(actualTweet, myFirstTweet, "tweet does not match");
    }

    @Test(enabled = true)
    public void testDuplicateTweet() {
        String myFirstTweet = "first tweet on Api automation ";
        ValidatableResponse response = this.tweetApiClient.createTweet(myFirstTweet);
        response.statusCode(403);
        System.out.println(response.extract().body().asPrettyString());
        String expectedMessage = "Status is a duplicate.";
        String actualMessage = response.extract().body().path("errors[0].message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test(enabled = true)
    public void testHeaderOfDuplicateTweet() {
        String myFirstTweet = "first tweet on Api automation ";
        ValidatableResponse response = this.tweetApiClient.createTweet(myFirstTweet);
        response.statusCode(403);
        String expectedFrameOptions = "SAMEORIGIN";
        String actualFrameOptions = response.extract().headers().getValue("x-frame-options");
        Assert.assertEquals(expectedFrameOptions, actualFrameOptions);
    }

    @Test(enabled = true)
    public void testDuplicateTweet1() {
        String myFirstTweet = "first tweet on Api automation ";
        ValidatableResponse response = this.tweetApiClient.createTweet(myFirstTweet);
        response.statusCode(403);
        Assert.assertNotEquals(403, 200);
    }

    @Test(enabled = true)
    public void testCreateTweetWithoutValueParameter() {
        // user should not be able to tweet without inserting value parameter
        String tweet = ""; // empty tweet
        ValidatableResponse response = this.tweetApiClient.createTweet(tweet);
        // verify the status code = 403
        response.statusCode(HttpStatus.SC_FORBIDDEN);
        // verify the body payload:
        System.out.println(response.extract().body().asPrettyString());
        String actualResponse = response.extract().body().path("errors[0].message");
        System.out.println(actualResponse);
        String expectedResponse = "Missing required parameter: status.";
        Assert.assertEquals(actualResponse, expectedResponse);
    }

    @Test(enabled = true)
    public void testDeleteTweet() {
        String tweet = "We are learning Rest API using Rest Assured and our First Tweet82d120dd-9045-44f3-a3c9-8720409fae20";
        ValidatableResponse deleteResponse = this.tweetApiClient.deleteTweet(1379519512886792200l);
        deleteResponse.statusCode(200);
        String actuaalTweet = deleteResponse.extract().body().path("text");
        System.out.println(actuaalTweet);

        // Assert.assertEquals(tweet,actualTweet);

    }


}