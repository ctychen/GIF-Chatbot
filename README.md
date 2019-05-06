# Fancy Meme Chat Bot
*Made by: Claire Chen, Carl Lerdorf, Zachary Wang*
5/3/19

_Final Project for Mr. Shelby’s 5th period, 2018-2019 class._

### Hardware concept:
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
⋅⋅⋅ Web Fetcher/Scraper to pull memes from websites based on category
⋅⋅⋅⋅⋅⋅ Some system which will consistently filter 
⋅⋅⋅⋅⋅⋅ This needs to be figured out
⋅⋅⋅⋅⋅⋅⋅⋅⋅ If you want to just pull based on tags, there are sites like this: https://www.memecenter.com/search/pokemon https://www.memedroid.com/memes/tag/happy, which collect memes that have certain tags
⋅⋅⋅⋅⋅⋅ Get and analyse large data set
⋅⋅⋅ Display gif/image on screen
⋅⋅⋅ Analyzes text input from user and replies with relevant photo/meme/gif
⋅⋅⋅ Does something useful: opens programs, plays music, etc are sample ideas
⋅⋅⋅ “Play me some music”
⋅⋅⋅ It would be fun if it could tell you about the weather forecast if you ask it
⋅⋅⋅⋅⋅⋅ Yahoo Weather API
⋅⋅⋅⋅⋅⋅ “What’s the weather like at ...”
⋅⋅⋅ School appropriate demo mode (doesn’t pull memes off all of Reddit)


## Want-To-Haves:
⋅⋅⋅ Speech to text with mic instead of typing (for the pi)
⋅⋅⋅ Sentiment analysis
⋅⋅⋅ Rate memes (help the bot improve future results through user input)
⋅⋅⋅ Different modes
⋅⋅⋅⋅⋅⋅ SAFE mode for only memes that are work/school ok - no swearing, etc
⋅⋅⋅⋅⋅⋅  NSFW mode for unfiltered, all memes
⋅⋅⋅ Plays music
⋅⋅⋅⋅⋅⋅ Maybe have a keyword for that, if user types keyword + song, then it takes the substring after the keyword and searches for that on YouTube, then gives you top search result
⋅⋅⋅⋅⋅⋅ Discord bots for inspiration (https://github.com/jagrosh/MusicBot)
⋅⋅⋅⋅⋅⋅ Maybe also finds music relevant to the sentiment analysis results

## Stretch features:
⋅⋅⋅ Pi screen on gimbal that always points towards the victim 
⋅⋅⋅ Face tracking
⋅⋅⋅ Audio triggered like Alexa 
⋅⋅⋅ auTo gEnERAted muSIc 
⋅⋅⋅ aUTo GEneRATed CLEAN meMEs
⋅⋅⋅ See also: Subreddit Simulator
https://arxiv.org/abs/1806.04510 
⋅⋅⋅⋅⋅⋅ “What are you doing with that supercomputer? I heard it’s neural network stuff”
⋅⋅⋅⋅⋅⋅ “.....uh…..”
⋅⋅⋅ Multiple personalities

## Class List:
User interface
Meme getter (Netflix parsing code)
Or jsoup
After a search query, cash all results and pick a random one, cashed memes should only stay cashed for a limited time, like an hour or something
Thing that processes text input and figures out what to do (send a meme? Open a program? Play a song ---> open YouTube and return top result?)
Sentiment analyzer
Tuna fish

## Responsibilities:
By May 5th, figure out how we are going to get the memes
How to sign up for API and get key: https://developers.giphy.com/docs/?fbclid=IwAR0LeZhekhkhHI45xgOw7kA0wPtruxVa1NK6oV10pA7zMiakJ074PoZECe4
https://tenor.com/gifapi/documentation?fbclid=IwAR3ayXHHmVVmmiokVI3NMl24QI7kgihKfSyud31nD_zXHyHmDIf_I9DTpjk#contentfilter
Carl - Do things
Claire - Do things
Zach - Do things

## Credits:
StackOverflow for lowkey everything

Tenor API Key: OTIWU6LM9JVL
