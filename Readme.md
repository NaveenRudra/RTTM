<h1>Real Time Threat Monitoring Tool</h1>
<img src="RTTI_Logo.png" style='width:50%;height:50%'/>
<h2>Description</h2>
RTTI (Real Time Threat Monitoring Tool) is a tool developed to scrap all pasties,github,reddit..etc in real time to identify occurrence of search terms configured. Upon match an email will be triggered. Thus allowing company to react in case of leakage of code, any hacks tweeted..etc.. and harden themselves against an attack before it goes viral.

The same tool in malicious user hands can be used offensively to get update on any latest hacks, code leakage etc..

List of sites which will be monitored are:
<ul>
<li>Non-Pastie Sites</li>
<ul>
<li>Twitter</li>
<li>Reddit</li>
<li>Github</li>
</ul>
<li>Pastie Sites</li>
<ul>
<li>Pastebin.com</li>
<li>Codepad.org</li>
<li>Dumpz.org</li>
<li>Snipplr.com</li>
<li>Paste.org.ru</li>
<li>Gist.github.com</li>
<li>Pastebin.ca</li>
<li>Kpaste.net</li>
<li>Slexy.org</li>
<li>Ideone.com</li>
<li>Pastebin.fr</li>
</ul>
</ul>
For architecture information and details of how this tool work refer documnetation folder in this repository.

<h3>Configuration</h3>

Before using this tool is is neccessary to understand the properties file present in scrapper_config directory.  
<ul>
<li>consumer.properties: Holds all the neccessary config data needed for consumer of Kafka (Refer apache Kafka guide for more information). The values present here are default options and does nto require any changes</li>  
<li>producer.properties: Holds all the neccessary config data needed for Producer (Refer apache Kafka guide for more information).The values present here are default options and does nto require any changes</li>  
<li>email.properties: Holds all the configuration data to send email.</li>  
<li>scanner-configuration.properties: This is the core configuration file. Update all the config for enabling search on twitter/github(To get tokens and key refer respective sites). For  pastie sites and reddit there is no need for any changes in config.</li>  
Note:However in all cases make sure to change "searchterms" to values of our choice to search. If there are multiple search terms then add them seperate by comma like the example data provided in config file. </br>
Understanding more about scanner-configuration.properties file.
<ul>
<ul>
For any pastie site configuration is as below:
Note:leave the pastie sites configuration as is and just change the search terms as requried by the organization. Thsi will do good.
<ul>
<li>scrapper.(pastie name).profile=(Pastie profile name)</li>
<li>scrapper.(pastie name).homeurl=(URL from where pastie ids a extracted)</li>
<li>scrapper.(pastie name).regex=(Regex to fetch pastie ids)</li>
<li>scrapper.(pastie name).downloadurl= (URL to get information about each apstie)</li>
<li>scrapper.(pastie name).searchterms=(Mention terms to be searched seperated by comma)</li>
<li>scrapper.(pastie name).timetosleep=(Time for which pastie thread will sleep before fetching pastie ids again)</li>
</ul>
</ul>
<ul>
For github search configuration is as below:
<ul>
<li>scrapper.github.profile=Github</li>
<li>scrapper.github.baseurl=https://api.github.com/search/code?q={searchTerm}&sort=indexed&order=asc</li>
<li>scrapper.github.access_token=(Get your own github access token)</li>
<li>scrapper.github.searchterms=(Mention terms to be searched seperated by comma)</li>
<li>scrapper.github.timetosleep=(Time for which github thred should sleep before searching again)</li>
</ul>
</ul>

<ul>
For reditt search configuration is as below:
<ul>
<li>scrapper.reddit.profile=Reddit</li>
<li>scrapper.reddit.baseurl=https://www.reddit.com/search.json?q={searchterm}</li>
<li>scrapper.reddit.searchterms=(Mention terms to be searched seperated by comma)</li>
<li>scrapper.reddit.timetosleep=(Time for which github thred should sleep before searching again)</li>
</ul>
</ul>

<ul>
For Twitter search configuration is as below:
<ul>
<li>scrapper.twitter.apikey=test</li>
<li>scrapper.twitter.profile=Twitter</li>
<li>scrapper.twitter.searchterms=(Mention terms to be searched seperated by comma)</li>
<li>scrapper.twitter.consumerKey=(Get your own consumer key)</li>
<li>scrapper.twitter.consumerSecret=(Get your own consumerSecret)</li>
<li>scrapper.twitter.accessToken=(Get your own accessToken)</li>
<li>scrapper.twitter.accessTokenSecret=(Get your own accessTokenSecret)</li>
</ul>
</ul>

</ul>
</ul>

<h3>How to use the tool</h3> 
<ul>
<li>Install JDK</li> 
<li>Install mvn and set the path</li> 
<li>Start the zookeeper and Kafka Server (Refer https://kafka.apache.org/documentation/#quickstart for more information) </li>
<ul>
<li>
Commands needed to start kafka in windows:
<ul>
<li>zooper-server-start.bat ../../config/consumer.properties</li>
<li>kafka-server-start.bat ../../config/server.properties</li>
<li>kafka-topics.bat --create --zookeeper localhost:2181  --replication-factor 1 --partitions 1 --topic "Kafka Topic name"</li>
</ul>
</li>
<li>
Commands needed to start kafka in linux:
<ul>
<li>zooper-server-start.sh ../config/consumer.properties
<li>kafka-server-start.sh ../config/server.properties
<li>kafka-topics.bat --create --zookeeper localhost:2181  --replication-factor 1 --partitions 1 --topic "Kafka Topic name"
</ul>
</li>

</ul>    
<li>Use kafka topic created in previous step </li> 
<li>Navigate to "rts" folder. Run command "mvn clean install -DskipTests". This willbuild the code.</li> 
<li>Navigate to scraptool/tartget  </li> 
<li>Run the command "java -jar scraptool-1.0-SNAPSHOT-standalone.jar -t "Kafka Topic name" -c "complete path of config directory"" </li>   
</ul>


<b>Authors:</b>
<ul>
<li>Naveen Rudrappa </li>                                                                                               
</ul>
