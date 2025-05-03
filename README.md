Résumé exécutif:

Ce mini‑framework Java illustre la mise en œuvre des principes d’Inversion de Contrôle (IoC) et d’Injection de Dépendances (DI) via deux modes de configuration :

XML (fichier beans.xml parsé avec JAXB)

Annotations (@Component, @Inject, @PostConstruct scannées dynamiquement avec Reflections)

L’objectif pédagogique est de comprendre comment décrire, instancier et relier des composants Java sans « new » explicite, en s’inspirant du conteneur Spring IoC. 

1) Contexte et objectifs
   
L’Inversion de Contrôle confie au conteneur la création et la gestion des objets d’une application, améliorant la maintenabilité et la testabilité 

L’Injection de Dépendances (DI) est un pattern permettant d’injecter automatiquement les dépendances d’un objet, soit via le constructeur, soit via un setter, soit directement sur le champ
Ce projet vise à reproduire ces mécanismes de manière minimaliste :

1. Configuration XML avec JAXB (Java Architecture for XML Binding)
2. Configuration Annotations selon JSR‑330 (@Inject)

Architecture des Beans
Chaque bean est défini par un BeanDefinition contenant :
- Un identifiant unique (id)
- Une classe (className)
- Des arguments de constructeur (constructor-arg)
- Des propriétés à injecter (property)
Le XmlApplicationContext lit beans.xml via JAXB, créé les instances, injecte par constructeur, setter ou champ, puis appelle init-method si défini.
Structure des Packages net.yassir
net.yassir.context : XmlApplicationContext et AnnotationApplicationContext
net.yassir.annotation : @Component, @Inject, @PostConstruct
net.yassir.dao : IDao, DaoImpl (retourne une donnée fixe)
net.yassir.metier : IMetier et implémentations (setter, constructor, field)
net.yassir.presentation : classes main pour tester chaque mode d’injection


2) Configuration XML :
   
1. Fichier beans.xml

Déclare les <bean id="..." class="...">

Supporte <constructor-arg>, <property> et init-method

2. Parsing avec JAXB

Lecture et mapping de l’XML vers des objets BeanDefinition
3. Injection

Constructeur si <constructor‑arg> spécifié

Setter via invocation de setXxx(...)

Field‑injection en fallback si pas de setter

4. Callback init-method

Appel d’une méthode sans paramètre après injection comme Spring OXM

3) Configuration par annotations :
   
Scan de classes

1. Utilisation du library Reflections pour détecter @Component dans le package racine

2. Instanciation

Par défaut : constructeur sans argument

Constructor‑injection : si un constructeur est annoté @Inject (JSR‑330)
3. Injection dynamique

Field‑injection : tous les champs annotés @Inject

Setter‑injection : toutes les méthodes @Inject à un paramètre
4. Post‑construction

Exécution des méthodes @PostConstruct

4) Architecture du projet :
   
net.yassir.dao

IDao et DaoImpl (retourne une valeur fixe, e.g. 42)

net.yassir.metier

IMetier et plusieurs implémentations :

MetierImpl2 (constructor‑injection XML)

MetierImpl3 (setter‑injection XML et field‑injection)

MetierImplConstructor / MetierImplSetter (annotation)

net.yassir.context

XmlApplicationContext et AnnotationApplicationContext

net.yassir.presentation

Points d’entrée main pour chaque mode d’injection

5) Résultats et captures d’écran :

   
5.1 XML:

pour les fichiers xml le traitement se fait via le fichier XmlAppliquationContext.java 
voici le resultat de l'injection a travers le fichier xml de configuration(beans.xml) on on recupere le resultat a partir des setters de MetierImpl3.java: 
![image](https://github.com/user-attachments/assets/488b80a9-7c85-43b8-8199-19c3c135aae9)

voici le resultat de l'injection a travers le fichier xml de configuration(beans.xml) on on recupere le resultat a partir du constructeur de MetierImpl2.java: 
![image](https://github.com/user-attachments/assets/215ff394-7896-4432-943e-416cd681e3e4)

voici le resultat de l'injection a travers le fichier xml de configuration(beans.xml), l'injection se fera par accès direct au champ MetierImpl3.java: 
![image](https://github.com/user-attachments/assets/72b4dbdd-55be-40ee-a00f-9038385471e1)

5.2 Annotations:

injection directe via attribu: 
![image](https://github.com/user-attachments/assets/0db4f45d-958f-48bc-9342-73470bd9bf75)

injection via contructeur: 
![image](https://github.com/user-attachments/assets/68c01592-44df-478d-bf4a-4f09d055fb24)

injection via setter: 
![image](https://github.com/user-attachments/assets/059650c1-e8ca-411d-b34b-22f6eb10254a)

6) Conclusion :
   
Ce mini projet sert de base pédagogique pour comprendre les principes fondamentaux de l’inversion de contrôle, de l’injection de dépendances et du traitement de configurations XML et annotations en Java.
il offre un laboratoire pratique pour :

- Comprendre l’IoC et les différents types de DI
- Manipuler JAXB pour la configuration XML
- Explorer la puissance des annotations JSR‑330 et du scanning runtime
- Se préparer à des frameworks plus complets comme Spring  en saisissant les principes fondamentaux du couplage faible et de l’injection de dépendances.





