
# Documentation API de Test

Ce document fournit une vue d'ensemble des différentes requêtes API que vous pouvez utiliser pour tester les différentes fonctionnalités de votre application. Vous trouverez des exemples de requêtes pour récupérer des données, créer, mettre à jour et supprimer des ressources, ainsi que des instructions pour les utiliser avec **curl** et **Postman**.

---

## Endpoints

### 1. **Récupérer tous les projets paginés**

**URL :** `/projets?page=0&size=10`

#### Description :
Cette requête permet de récupérer une liste paginée de projets.

#### Exemple avec `curl` :
```bash
curl -X GET "http://localhost:8081/projets?page=0&size=10" -H "accept: application/json"
```

#### Exemple avec **Postman** :
- **Méthode :** GET
- **URL :** `http://localhost:8081/projets?page=0&size=10`
- **Headers :** `accept: application/json`

---

### 2. **Récupérer un projet par ID**

**URL :** `/projets/{id}`

#### Description :
Récupère les informations d'un projet spécifique en utilisant son ID.

#### Exemple avec `curl` :
```bash
curl -X GET "http://localhost:8081/projets/1" -H "accept: application/json"
```

#### Exemple avec **Postman** :
- **Méthode :** GET
- **URL :** `http://localhost:8081/projets/1`
- **Headers :** `accept: application/json`

---

### 3. **Créer un nouveau projet**

**URL :** `/projets/add`

#### Description :
Crée un projet avec les informations spécifiées dans le corps de la requête.

#### Exemple avec `curl` :
```bash
curl -X POST "http://localhost:8081/projets/add" -H "Content-Type: application/json" -d '{"name":"Nouveau Projet","description":"Description du projet"}'
```

#### Exemple avec **Postman** :
- **Méthode :** POST
- **URL :** `http://localhost:8081/projets/add`
- **Headers :** `Content-Type: application/json`
- **Body :**
  ```json
  {
    "name": "Nouveau Projet",
    "description": "Description du projet"
  }
  ```

---

### 4. **Mettre à jour un projet**

**URL :** `/projets/{id}`

#### Description :
Met à jour un projet existant en fonction de son ID.

#### Exemple avec `curl` :
```bash
curl -X PUT "http://localhost:8081/projets/1" -H "Content-Type: application/json" -d '{"name":"Projet Modifié","description":"Nouvelle description"}'
```

#### Exemple avec **Postman** :
- **Méthode :** PUT
- **URL :** `http://localhost:8081/projets/1`
- **Headers :** `Content-Type: application/json`
- **Body :**
  ```json
  {
    "name": "Projet Modifié",
    "description": "Nouvelle description"
  }
  ```

---

### 5. **Supprimer un projet**

**URL :** `/projets/{id}`

#### Description :
Supprime un projet en fonction de son ID.

#### Exemple avec `curl` :
```bash
curl -X DELETE "http://localhost:8081/projets/1" -H "accept: application/json"
```

#### Exemple avec **Postman** :
- **Méthode :** DELETE
- **URL :** `http://localhost:8081/projets/1`
- **Headers :** `accept: application/json`

---

### 6. **Récupérer les statistiques de comptage des tâches par projet (paginé)**

**URL :** `/count/all?page=0&size=5`

#### Description :
Cette requête récupère une page de projets avec le nombre de tâches associées à chaque projet.

#### Exemple avec `curl` :
```bash
curl -X GET "http://localhost:8081/count/all?page=0&size=5" -H "accept: application/json"
```

#### Exemple avec **Postman** :
- **Méthode :** GET
- **URL :** `http://localhost:8081/count/all?page=0&size=5`
- **Headers :** `accept: application/json`

---

### 7. **Récupérer une tâche par ID**

**URL :** `/tasks/{id}`

#### Description :
Récupère une tâche spécifique par son ID.

#### Exemple avec `curl` :
```bash
curl -X GET "http://localhost:8081/tasks/1" -H "accept: application/json"
```

#### Exemple avec **Postman** :
- **Méthode :** GET
- **URL :** `http://localhost:8081/tasks/1`
- **Headers :** `accept: application/json`

---

### 8. **Récupérer toutes les tâches par titre**

**URL :** `/tasks/title/{title}?page=0&size=5`

#### Description :
Récupère une liste de tâches par leur titre. Utilisez la pagination pour limiter les résultats.

#### Exemple avec `curl` :
```bash
curl -X GET "http://localhost:8081/tasks/title/TaskTitle?page=0&size=5" -H "accept: application/json"
```

#### Exemple avec **Postman** :
- **Méthode :** GET
- **URL :** `http://localhost:8081/tasks/title/TaskTitle?page=0&size=5`
- **Headers :** `accept: application/json`

---

## Tester avec **Postman**

1. Téléchargez [Postman](https://www.postman.com/downloads/).
2. Ouvrez Postman et créez une nouvelle requête avec la méthode HTTP appropriée (GET, POST, PUT, DELETE).
3. Entrez l'URL de l'API.
4. Configurez les en-têtes (headers) et le corps de la requête (body) si nécessaire.
5. Cliquez sur "Send" pour envoyer la requête.

---

## Tester avec **curl**

1. Assurez-vous que `curl` est installé sur votre machine.
2. Ouvrez un terminal.
3. Entrez les commandes `curl` mentionnées ci-dessus pour tester chaque endpoint.
4. Vous verrez les réponses directement dans votre terminal.

---

## Gestion du Cache

Les endpoints utilisant le cache sont les suivants :
- **`/tasks`** : Récupération paginée des tâches, cache activé.
- **`/tasks/title/{title}`** : Recherche par titre, cache activé.
- **`/tasks/{id}`** : Récupération par ID, cache activé.

Pour invalider le cache, toute modification (création, mise à jour, suppression) d'une tâche ou d'un projet entraîne l'effacement du cache.

---

## Conclusion

Ces tests vous permettent de valider toutes les fonctionnalités de votre API avec pagination, mise à jour, création et suppression des ressources, ainsi que la gestion du cache pour améliorer les performances. N'oubliez pas d'ajuster les paramètres (comme `page` et `size`) selon vos besoins pour tester différentes tailles de page.