Output
===
```Java
$ java WordNet data / synsets . txt data / hypernyms . txt worm bird
# of nouns = 119188
isNoun ( worm ) = true
isNoun ( bird ) = true
isNoun ( worm bird ) = false
sca ( worm , bird ) = animal animate_being beast brute creature fauna
distance ( worm , bird ) = 5
```
```Java
$ java S h o r t e s t C o m m o n A n c e s t o r data / digraph1 . txt
3 10 8 11 6 2
< ctrl -d >
length = 4 , ancestor = 1
length = 3 , ancestor = 5
length = 4 , ancestor = 0
```
```Java
$ java Outcast data / synsets . txt data / hypernyms . txt data / outcast5 . txt data / outcast8 . txt data / outcast11 . txt
outcast ( data / outcast5 . txt ) = table
outcast ( data / outcast8 . txt ) = bed
outcast ( data / outcast11 . txt ) = potato
```
```Python
python3 run_tests.py -v [<problems>] //python check out
```
