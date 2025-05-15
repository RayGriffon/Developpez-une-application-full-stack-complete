# P6-Full-Stack-reseau-dev

## Front

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

Don't forget to install your node_modules before starting (`npm install`).

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Installation

Le script pour créer une BDD vide se trouve ici : https://github.com/RayGriffon/Developpez-une-application-full-stack-complete/blob/main/MDD_BDD
Il s'agit d'une base MySQL utilisant le port 3306

Il faut configurer en variable d'environnement les trois données suivantes :
- Pour la BDD : username (**DB_USERNAME**), password (**DB_PASSWORD**)
- Pour le token : la secret key (**JWT_SECRET_KEY**)

Dans le fichier application.properties se trouve les variables configurables suivantes :
- Le lien vers la base de donnée : **spring.datasource.url**
