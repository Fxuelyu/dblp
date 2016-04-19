for $x in doc("book.xml")/books/book
where $x/price>30
return $x/title