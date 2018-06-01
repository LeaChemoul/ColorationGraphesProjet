


-------------------------------------------------------
L'outil GRAPHVIZ
-------------------------------------------------------

La partie suivante explique comment j'ai généré les images de graphes du projet.
Elle n'a pas pour but d'être proposé dans le projet final.
Cependant si vosu souhaitez voir à quoi ressemble les graphes que vous manipulez,
voici comment j'ai procédé pour les génerer.

Nous générons des fichiers .dot de nos graphes grace à la méthode generateFile de la classe GrapheListe;
Ces fichiers ont pour but de generer ensuite une image .png du graphe correspondant à partir du logiciel GraphVIZ.
Ce logiciel s'installe de la mùanière suivante :
- telecharger graphvis.msi sur https://graphviz.gitlab.io/_pages/Download/Download_windows.html
- ouvrez avec un clic droit sur ordinateur -> propriétés -> Modifier les paramètres -> Variables d'environnement
- créez ou modifier la variable PATh avec : 
	le path vers le repertoire bin de graphviz qui se trouve ou vous avez installé graphviz
		par exemple : C:\Program Files\Graphviz2.38\bin
	pesnsez à mettre un ';' à la fin de la valeur de la variable d'environnement.
- ouvrez une console (commande cmd sous windows)
- placez vous dans le repertoire ou se trouve le fichier .dot avec la configuration de votre graphe.
- tapez la commande suivante : dot -Tpng votreFichier.dot -o votreFichier_dot.png

Ainsi, dans le même repertoire vous trouverez le fichier png généré.

-------------------------------------------------------