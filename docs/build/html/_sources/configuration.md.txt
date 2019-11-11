Configuration
=============

Before using this tool is is neccessary to understand the properties file present in scrapper_config directory.

**consumer.properties**
------------------------
 Holds all the neccessary config data needed for consumer of Kafka (Refer apache Kafka guide for more information). The values present here are default options and does nto require any changes

producer.properties
------------------------
 Holds all the neccessary config data needed for Producer (Refer apache Kafka guide for more information).The values present here are default options and does nto require any changes

email.properties
------------------------
 Holds all the configuration data to send email.

scanner-configuration.properties
-------------------------------------
 This is the core configuration file. Update all the config for enabling search on twitter/github(To get tokens and key refer respective sites).

For pastie sites and reddit there is no need for any changes in config.

*Note:However in all cases make sure to change "searchterms" to values of our choice to search. If there are multiple search terms then add them seperate by comma like the example data provided in config file.*

**Understanding more about scanner-configuration.properties file.**

For any pastie site configuration is as below: 

*Note:leave the pastie sites configuration as is and just change the search terms as requried by the organization. This will do good.*

* scrapper.(pastie name).profile=(Pastie profile name)

* scrapper.(pastie name).homeurl=(URL from where pastie ids a extracted)

* scrapper.(pastie name).regex=(Regex to fetch pastie ids)

* scrapper.(pastie name).downloadurl= (URL to get information about each apstie)

* scrapper.(pastie name).searchterms=(Mention terms to be searched seperated by comma)

* scrapper.(pastie name).timetosleep=(Time for which pastie thread will sleep before fetching pastie ids again)

For github search configuration is as below:

* scrapper.github.profile=Github

* scrapper.github.baseurl=https://api.github.com/search/code?q={searchTerm}&sort=indexed&order=asc

* scrapper.github.access_token=(Get your own github access token)

* scrapper.github.searchterms=(Mention terms to be searched seperated by comma)

* scrapper.github.timetosleep=(Time for which github thred should sleep before searching again)


For reditt search configuration is as below:
* scrapper.reddit.profile=Reddit

* scrapper.reddit.baseurl=https://www.reddit.com/search.json?q={searchterm}

* scrapper.reddit.searchterms=(Mention terms to be searched seperated by comma)

* scrapper.reddit.timetosleep=(Time for which github thred should sleep before searching again)


For Twitter search configuration is as below:
* scrapper.twitter.apikey=test

* scrapper.twitter.profile=Twitter

* scrapper.twitter.searchterms=(Mention terms to be searched seperated by comma)

* scrapper.twitter.consumerKey=(Get your own consumer key)

* scrapper.twitter.consumerSecret=(Get your own consumerSecret)

* scrapper.twitter.accessToken=(Get your own accessToken)

* scrapper.twitter.accessTokenSecret=(Get your own accessTokenSecret)
