*Tender SQL Schema

**Tables

***Expenes
|Name          |Type         |
|:------------:|:-----------:|
|ID            |KEY          |
|Date          |DATE         |
|Descripton    |TEXT         |
|Category      |TEXT         |
|Amount        |DOUBLE       |
|User          |TEXT         |


***Categories
|Name          |Type         |
|:------------:|:-----------:|
|ID            |KEY          |
|Name          |TEXT         |
|Interval      |TEXT         |
|Budget        |DOUBLE       |
|Spent         |DOUBLE       |
|Start Date    |DATE         |
|End Date      |DATE         |
|Is Current    |BOOLEAN      |



***Users
|Name          |Type         |
|:------------:|:-----------:|
|ID            |KEY          |
|First Name    |TEXT         |
|Last Name     |TEXT         |
|Email         |TEXT         |
