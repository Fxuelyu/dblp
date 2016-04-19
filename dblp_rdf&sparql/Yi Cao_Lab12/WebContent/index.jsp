<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style> 
input[type=text] {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    box-sizing: border-box;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sparql queries</title>
</head>
<body>

<form method="GET" action="servlet">
  search:<br>
  Name: <input type="text" name="name"><br>
  YearRange: <input type="text" name="year1"> to <input type="text" name="year2"><br>
  Title:<input type="text" name="title"><br>
  Keyword:<input type="text" name="keyword"><br>
  <input type="radio" name="type" value="byName" checked>byName <br>
  <input type="radio" name="type" value="byRange">byRange<br>
  <input type="radio" name="type" value="byCo-authors"> byCo-authors<br>
  <input type="radio" name="type" value="byTitle">byTitle<br>
  <input type="radio" name="type" value="byKeywords">byKeywords<br>
  <input type="submit" value="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="reset"/>
</form>
</body>
</html>