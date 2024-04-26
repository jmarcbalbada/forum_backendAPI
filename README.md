# For debugging only

GET PAGINATED POST http://localhost:8000/posts?page={page}  
**METHOD:** GET


CREATE USER http://localhost:8000/users  
**METHOD:** POST  

{  
  "firstName": "samp1",  
  "lastName": "samp1"  
}  

USER LOGIN http://localhost:8000/users/login  
**METHOD:** POST  

{  
  "firstName": "UpdatedFirstName",  
  "lastName": "UpdatedLastName"  
}  

CREATE POST:http://localhost:8000/posts  
**METHOD:** POST  

{  
    "userId": 1,  
    "postText": "This is the content of the new post."  
}  


UPDATING POST http://localhost:8000/posts/{id}  
**METHOD:** PUT:  

{  
  "id": 4,  
  "user": {  
    "id": 4,  
    "firstName": "jm",  
    "lastName": "balbs"  
  },  
  "postText": "Updated post content"  
}  


UPDATING USER http://localhost:8000/users/{id}  
**METHOD:** PUT  

{  
  "firstName": "UpdatedFirstName",  
  "lastName": "UpdatedLastName"  
}  


DELETE USER: http://localhost:8000/users/{id}  
**METHOD:** DELETE


DELETE POST: http://localhost:8000/posts/{id}  
**METHOD:** DELETE:

REPLY:
CREATE REPLY: http://localhost:8000/posts/1/replies  
**METHOD:** POST  

{  
    "userId": 1,  
    "replyText": "This is a reply to the post."  
}  

UPDATE REPLY: http://localhost:8000/posts/1/replies/{id}  
**METHOD:** PUT  

{  
    "replyText": "This is the updated reply text."  
}  

DELETE REPLY: http://localhost:8000/posts/1/replies/{id}
**METHOD:** DELETE

