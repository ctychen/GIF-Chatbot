# Fancy Meme Chat Bot
Made by: Claire Chen, Carl Lerdorf, Zachary Wang
5/3/19

_Final Project for Mr. Shelby’s 5th period, 2018-2019 class._

## Hardware concept:
![idea](https://i.imgur.com/z820oNc.png)

## Description:
	A chat bot that responds to you with memes based on a user’s text input and mood.
	Also does helpful little tasks
	TLDR: Alexa with memes

## Instructions:
	Type some garbage and look at memes.
	Type certain keywords to activate features

## Features:
### Must Haves:
Pull memes from websites based on category


Get and analyse large data set


Display gif/image on screen


Analyzes text input from user and replies with relevant photo/meme/gif


Does something useful: opens programs, plays music, etc are sample ideas (“Play me some music” for example. 
It would also be fun if it could tell you about the weather forecast if you ask it; “What’s the weather like at ________”


School appropriate demo mode (doesn’t pull memes off all of Reddit)


### Want-To-Haves:
Speech to text with mic instead of typing (for the pi)


Sentiment analysis


Rate memes (help the bot improve future results through user input)


Different modes: SAFE mode for only memes that are work/school ok - no swearing, etc; NSFW mode for unfiltered, all memes


Maybe also finds music relevant to the sentiment analysis results

### Stretch features:
Pi screen on gimbal that always points towards the victim 


Face tracking


Audio triggered like Alexa 


auTo gEnERAted muSIc 


aUTo GEneRATed CLEAN meMEs


Multiple personalities


## Class List:
User interface/ChatWindow

MemeGetter (JSON parsing):

Has-a cache of JSON files and a cache of meme images already loaded. Parses the JSON when necessary
After a search query, cash all results and pick a random one, cached memes should only stay cached for a limited time, like an hour or something


SmartBoi: 
Thing that processes text input and figures out what to do (send a meme? Open a program? Play a song?)


TextAnalyzer
Parses user input and looks for keywords, which will be used by TunaFish and SmartBoi


Tuna fish/MemeRater
Handles rating memes and choosing a meme out of queued ones to show, based on analyzed sentiment of user


ActionDoer


Meme


AnimatedGIF


Cache

## Responsibilities:
By May 5th, figure out how we are going to get the memes
How to sign up for API and get key: https://developers.giphy.com/docs/?fbclid=IwAR0LeZhekhkhHI45xgOw7kA0wPtruxVa1NK6oV10pA7zMiakJ074PoZECe4
https://tenor.com/gifapi/documentation?fbclid=IwAR3ayXHHmVVmmiokVI3NMl24QI7kgihKfSyud31nD_zXHyHmDIf_I9DTpjk#contentfilter


Carl - Do things


Claire - Do things


Zach - Do things

## Credits:
StackOverflow for lowkey everything


Mr Lerdorf for PHP script test


Md5: https://www.geeksforgeeks.org/md5-hash-in-java/


Creating a file: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java


Get time stamp: https://stackoverflow.com/questions/24804618/get-file-mtime-with-millisecond-resolution-from-java
	
