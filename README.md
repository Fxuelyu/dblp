# dblp

 

```bash
# This project helps to parse dblp and create Restful Service
```
## Requirements1: 

* Given author name A, list all of her co-authors.

  * Note: A person B may have co-authored with A for multiple papers, but only count as one co-author.

* Given an exact paper name, list its publication details.

* Given an author name, list all of her publications and detailed publication information.

  * Note: No need to consider authors with the same name.

* Given some keywords, list all publications that contain some or all of the keywords in the paper title.

* Given two author names, find out whether they ever co-author some papers and if yes, the details.

## Requirements2:
* create a invokable publication web service. 

  * Note：Mainly, practice three major techniques: XQuery, XSLT transformation, and WSDL service publication and invocation.

* Select all the article titles;

* Select the title of the first article;

* Select all the authors;

* Select article nodes with publication data later than 2010;

* Select title nodes with publication data later than 2010;

    

## Requirement3:

* write an XSLT template to create HTML pages to display the results of the above queries.

* write an HTML page to allow users to select from the above five queries, together with user input (e.g., year).

* When a user selects an option, it will trigger an XQuery-based action, and then the results will be shown in an HTML file. 

* Write a file Transform.java that takes three parameters: the 1st parameter is the XML file; the 2nd parameter is the XSLT stylesheet; and the 3rd parameter is the output HTML file.  

* Meanwhile, you will provide web service option to your publication management system, in addition to being a web application.

* Develop a Web Service Endpoint Interface;

* Develop a Web Service Endpoint Implementation;

* Develop an Endpoint Publisher;

* Develop Java Web Service Client (without tool);

* Develop Java Web Service Client via wsimport tool   

 

## Requirement4:

* use Lucene that is an open-source search library to index the information collection and associated annotations.

* design search engine will support queries both on textual attributes (like paper title and author name) and allow 

* simple keyword-based searches on textual attributes. 

