# Infographie
On se propose de réaliser un logiciel permettant de générer une carte en trois dimensions et de l’exploiter pour en tirer
des données ou la visualiser.
## Cartes
Une carte est un ensemble de points (x, y, h) où x et y représentant la localisation géographique sur un plan et h la
hauteur (ou l’élévation). Une carte est donc une discrétisation d’une carte altimétrique à courbes de niveaux.
La génération d’une carte un brin réaliste nécessite l’emploi d’algorithmes adéquats car l’on souhaite obtenir des
montagnes (variations brutales de la hauteur pour des points voisins), des plaines (variations plus lisses), etc.
Il existe de nombreux algorithmes de génération de carte mais l’on peut commencer par les algorithmes de point-milieu,
point-milieu récursif, diamant-carré, bruit de Perlin, etc. On trouvera sur Internet de nombreuses resources à ce sujet
(Terrain Generation, Map Generation, etc).
Dans cette partie, il est donc demandé d’implémenter au moins deux algorithmes de génération de carte avec éventuellement
des contraintes qui pourraient être précisées par l’utilisateur : point culminant ici, point bas là, plaine ici et là,
altitude de la mer, lac ici, altitude des neiges, des forêts, etc.

## Parcours
Étant donnée une carte, on souhaite pouvoir calculer :
• la courbe des altitudes du plus court chemin entre deux points,
• la distance et les altitudes d’un plus court chemin entre deux points étant données quelques contraintes : éviter
telle région de la carte, éviter les segments qui montent trop, etc.

## Survol
Une fois la carte générée, l’utilisateur doit pouvoir la visualiser par projection de manière réaliste (c’est-à-dire au
minimum avec les parties cachées), c’est-à-dire avec des couleurs adéquates, etc.
Dans un second temps, il devrait être possible d’ajouter le soleil à différentes heures de la journée (soleil couchant,
midi, etc) afin d’observer des ombres portées, des effets de lumière (sur la neige, la mer), etc.
En sur-impression, on devrait pouvoir afficher un chemin (comme calculé précédemment) sur cette carte.

###### Université Paris Diderot - Infographie
