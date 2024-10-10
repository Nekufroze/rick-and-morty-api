# Project Architecture

## Introduction
Ce projet est développé en utilisant Kotlin et Java avec Gradle comme système de build.
Il suit une architecture modulaire et utilise des concepts tels que les coroutines et Jetpack Compose pour la partie UI.

## Structure du Projet

### Modules
Le projet est divisé en plusieurs modules pour une meilleure séparation des préoccupations et une maintenabilité accrue :
- **app** : Contient le code de l'application principale.
- **core** : Contient les modèles de données et les interfaces des repositories.
- **features** : Contient les différentes fonctionnalités de l'application, comme les détails des personnages.

### Domain Layer
Le module `domain` contient les modèles de données et les interfaces des repositories. 