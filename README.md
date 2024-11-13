
# Documentation des API 

## Table des matières
- [Projet](#projet)
  - [GET /projets](#get-projets)
  - [POST /projets/add](#post-projets)
- [Task](#task)
  - [GET /tasks](#get-tasks)
  - [POST /tasks/add](#post-tasks)
- [User](#user)
  - [GET /users](#get-users)
  - [POST /users/add](#post-users)
- [ProjetTaskCount](#projet-task-count)
  - [GET /count](#get-count)
  - [POST /count](#post-count)

## Projet

### GET /projets
**URL**: `/projets`

**Méthode**: GET

**Paramètres**:
- `page`: numéro de la page (par défaut `0`)
- `size`: nombre d'éléments par page (par défaut `5`)

**Exemple de requête**:
```bash
curl -X GET "http://localhost:8081/projets?page=0&size=5"
```

**Réponse**:
```json
[
  {
    "id": 1,
    "name": "Projet 1",
    "description": "Description du projet 1"
  },
  {
    "id": 2,
    "name": "Projet 2",
    "description": "Description du projet 2"
  }
]
```

### POST /projets/add
**URL**: `/projets/add`

**Méthode**: POST

**Body**:
```json
{
  "name": "Nouveau Projet",
  "description": "Description du nouveau projet"
}
```

**Exemple de requête**:
```bash
curl -X POST "http://localhost:8081/projets/add" -H "Content-Type: application/json" -d '{"name": "Nouveau Projet", "description": "Description du nouveau projet"}'
```

**Réponse**:
```json
{
  "id": 3,
  "name": "Nouveau Projet",
  "description": "Description du nouveau projet"
}
```

## Task

### GET /tasks
**URL**: `/tasks`

**Méthode**: GET

**Paramètres**:
- `page`: numéro de la page (par défaut `0`)
- `size`: nombre d'éléments par page (par défaut `10`)

**Exemple de requête**:
```bash
curl -X GET "http://localhost:8081/tasks?page=0&size=10"
```

**Réponse**:
```json
[
  {
    "id": 1,
    "title": "Task 1",
    "description": "Description de la tâche 1"
  },
  {
    "id": 2,
    "title": "Task 2",
    "description": "Description de la tâche 2"
  }
]
```

### POST /tasks/add
**URL**: `/tasks/add`

**Méthode**: POST

**Body**:
```json
{
  "title": "New Task",
  "description": "New task description"
}
```

**Exemple de requête**:
```bash
curl -X POST "http://localhost:8081/tasks/add" -H "Content-Type: application/json" -d '{"title": "New Task", "description": "New task description"}'
```

**Réponse**:
```json
{
  "id": 3,
  "title": "New Task",
  "description": "New task description"
}
```

## User

### GET /users
**URL**: `/users`

**Méthode**: GET

**Paramètres**:
- `page`: numéro de la page (par défaut `0`)
- `size`: nombre d'éléments par page (par défaut `5`)

**Exemple de requête**:
```bash
curl -X GET "http://localhost:8081/users?page=0&size=5"
```

**Réponse**:
```json
[
  {
    "id": 1,
    "name": "User 1",
    "email": "user1@example.com"
  },
  {
    "id": 2,
    "name": "User 2",
    "email": "user2@example.com"
  }
]
```

### POST /users/add
**URL**: `/users/add`

**Méthode**: POST

**Body**:
```json
{
  "name": "New User",
  "email": "newuser@example.com"
}
```

**Exemple de requête**:
```bash
curl -X POST "http://localhost:8081/users/add" -H "Content-Type: application/json" -d '{"name": "New User", "email": "newuser@example.com"}'
```

**Réponse**:
```json
{
  "id": 3,
  "name": "New User",
  "email": "newuser@example.com"
}
```

## ProjetTaskCount

### GET /count
**URL**: `/count`

**Méthode**: GET

**Paramètres**:
- `page`: numéro de la page (par défaut `0`)
- `size`: nombre d'éléments par page (par défaut `5`)

**Exemple de requête**:
```bash
curl -X GET "http://localhost:8081/count?page=0&size=5"
```

**Réponse**:
```json
[
  {
    "projet_id": 1,
    "name": "Projet 1",
    "taskCount": 3
  },
  {
    "projet_id": 2,
    "name": "Projet 2",
    "taskCount": 5
  }
]
```

### POST /count
**URL**: `/count`

**Méthode**: POST

**Exemple de requête**:
```bash
curl -X POST "http://localhost:8081/count" -H "Content-Type: application/json" -d '{}'
```

**Réponse**:
```json
{
  "message": "Materialized view refreshed successfully."
}
```

## Conclusion
Ces tests permettent de valider les différentes routes pour chaque entité et de tester les requêtes paginées ainsi que les opérations CRUD.
