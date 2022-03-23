Hi!  Really sorry there isn't a CLI;  I had a lot of schoolwork and didn't have time to finish one (although it's very easy to use with curl)

REST endpoints:

###`GET` /:word
200 if keyword is present;  404 otherwise.
```bash
curl host:4567/hello
-> Exact match for {hello} present
```

###`GET` /:word/completions
Returns a list of completions of `word`;  if word is not present, empty list
```bash
curl host:4567/hell/completions
-> [o]
```

###`POST` /:word
Adds a word;  204 if already present, 201 if added.
```bash
curl -XPOST host:4567/hello
-> {hello} already present.
```

###`DELETE` /:word
404 if keyword not present to begin with;  otherwise, returns 200 if successfully deleted.
```bash
curl -XPOST host:4567/hello
-> Removed word
```

###`GET` /
Returns the tree in string form
