Architecture
=============

.. image:: _static/architecture.png
  :width: 800
  :alt: Alternative text

The architectural diagram of the tool is as above.

**How it works**
-----------------

Once the tool is started , engine gets kicked off and it runs forever. The main input for this engine is the configuration file. Based on the configuration file data, engine goes ahead and probes twitter/github/reddit for matches configured in configuration file. Upon a match is found, the link of twitter/github/reddit pushed to sqlite DB and an email alert is triggered.

In case of pastie sites the logic is different. The reason being they do not support search nor streaming api's. Hence any new pastie made by any user, the link is fetched and pushed to kafka. From kafka any new link added is picked up and searched for matches configured in configuration file. Upon a match is found, the link of pastie site is pushed to sqlite DB and an email alert is triggered.