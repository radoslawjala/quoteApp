## Uruchomienie

Aplikację można uruchomić z poziomu IDE (np. Intellij IDEA) bądź z konsoli za pomocą komendy
```bash
gradlew bootRun
```

## Endpointy

* POST `/quotes` - Dodaje nowy cytat. Przykład: 
```bash
{
    "author": "Thomas Jefferson",
    "quote": "When injustice becomes law, resistance becomes duty"
}
```
Autor powinien zawierać 3-100 znaków, cytat 3-250 znaków. Cytat należy podać w `body` requestu.


* GET `/quotes` - Zwraca wszystkie cytaty.

* PUT `/quotes/{id}` - Aktualizuje cytat. W parametrze należy podać `id` cytatu a w `body` nowy cytat. W przypadku braku wystąpienia zwracany jest kod `400 bad request`
* DELETE `/quotes/{id}` - Usuwa cytat. W parametrze należy podać `id` cytatu. W przypadku braku wystąpienia zwracany jest kod `400 bad request`
