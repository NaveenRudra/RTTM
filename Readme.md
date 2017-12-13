<h2>Description</h2>
RTS (Realtime scrapper) is a tool developed to scrap all pasties,github,reddit..etc in real time to identify occurrence of search terms configured. Upon match an email will be triggered. Thus allowing company to react in case of leakage of code, any hacks tweeted..etc.. and harden themselves against an attack before it goes viral.

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
<li>Lpaste.net</li>
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
<li>consumer.properties: Holds all the neccessary config data needed for consumer (Refer apache Kafka guide for more information). The values present here are default options and does nto require any changes</li>  
<li>producer.properties: Holds all the neccessary config data needed for Producer (Refer apache Kafka guide for more information).The values present here are default options and does nto require any changes</li>  
<li>email.properties: Configure SMTP server with email id's.</li>  
<li>scanner-configuration.properties: This is the core configuration file. Update all the data of twitter for enabling search on twitter/github(To get tokens and key refer respective sites). For  pastie sites and reddit there is no need for any changes in config.</li>  
<ul>
Understanding more about scanner-configuration.properties file.
<ul>
For any pastie site configuration is as below:
<ul>
<li>scrapper.<pastie name>.profile=<Pastie profile name></li>
<li>scrapper.<pastie name>.homeurl=<URL from where pastie ids a extracted></li>
<li>scrapper.<pastie name>.regex=<Regex to fetch pastie ids></li>
<li>scrapper.<pastie name>.downloadurl= <URL to get information about each apstie></li>
<li>scrapper.<pastie name>.searchterms=<Mention terms to be searched seperated by comma></li>
<li>scrapper.<pastie name>.timetosleep=<Time for which pastie thread will sleep before fetching pastie ids again></li>
</ul>
</ul>
<ul>
For gituhb search configureation is as below:
<ul>
<li>scrapper.github.profile=Github</li>
<li>scrapper.github.baseurl=https://api.github.com/search/code?q={searchTerm}&sort=indexed&order=asc</li>
<li>scrapper.github.access_token=<Get your own github access token></li>
<li>scrapper.github.searchterms=<Mention terms to be searched seperated by comma></li>
<li>scrapper.github.timetosleep=<Time for which github thred should sleep before searching again></li>
</ul>
</ul>

<ul>
For reditt search configureation is as below:
<ul>
<li>scrapper.reddit.profile=Reddit</li>
<li>scrapper.reddit.baseurl=https://www.reddit.com/search.json?q={searchterm}</li>
<li>scrapper.reddit.searchterms=<Mention terms to be searched seperated by comma></li>
<li>scrapper.reddit.timetosleep=<Time for which github thred should sleep before searching again></li>
</ul>
</ul>
</ul>
However in all cases make sure to change "searchterms" to values of our choice to search. If there are multiple search terms then add them seperate by comma as shown with example terms in config file.
</ul>

<h3>How to use the tool</h3> 
<ul>
<li>Install JDK</li> 
<li>Install mvn and set the path</li> 
<li>Start the zookeeper and Kafka Server (Refer https://kafka.apache.org/documentation/#quickstart for more information) </li>    
<li>Create a kafka topic </li> 
<li>Navigate to "rts" folder. Run command "mvn clean install -DskipTests". This willbuild the code.</li> 
<li>Navigate to scraptool/tartget  </li> 
<li>Run the command "java -jar scraptool-1.0-SNAPSHOT-standalone.jar -t "Kafka Topic name" -c "complete path of config directory"" </li>   
</ul>

<h3>Contributors</h3>
<ul>
<li>Naveen Rudrappa </li>                                                                                                              
<li>Padma Shivakumar</li> 
</ul>
