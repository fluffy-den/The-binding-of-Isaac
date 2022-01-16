# The-binding-of-Isaac

 TP6 - Binôme 20
 NOE Louis-Quentin
 JULES Cyprien
 
 
 Les commandes en jeu :
 
 + Déplacements :
 
   - Z pour se déplacer vers le NORD (haut)
   - Q pour se déplacer vers l'OUEST (gauche)
   - S pour se déplacer vers le SUD  (bas)
   - D pour se déplacer vers l'EST   (droite)
   
 + Les touches d'attaque :
 
   - Flèches dirrectionels pour lancer une larme 
   - MAJ ou E pour placer une bombe
   
 + Les touches fonctionnels :
 
   - ESPACE pour charnger de niveau (après avoir tuer un boss et en se plaçant sur la trape)
   - ENTRER pour acheter un objet à la boutique (en se plaçant dessus)
   
 + Les touches de triche :
 
   - I pour rendre le hero invincible
   - L pour rendre le hero très rapide
   - K pour tuer l'intégralité des monstres d'une salle
   - P pour que les larmes du hero tue les monstres en un coup
   - O pour donner 10 pièces au hero
 
 NB: Les touches (hors cheat) sont rappelées du début du jeu
 
 
 
 
 
 
 
 La partie suivante traite de choix de développement et artistiques du jeu, des spoils peuvent êtres présents
 
 
 
 
 
 
 
 
 Choix sur la programation du jeu :
      
  La construction des dongeon : (Tous faits aléatoirement)
    
    Nous avons choisit de réaliser 5 niveaux distincts comprenant chacun un boss différent.
    La progression étant linéaire, un joueur ne peux pas choisir un niveau en particulier, il doit affronter tous les boss.
      NB: En utilisant les touches de tricher, nous pouvez fini le jeu en 3 à 4 minutes maximum (voir 30s dans les meilleurs cas)
    Les dongeons sont crées aléatoirement mais les boss sont placés dans un ordre précis (du au caractéristiques de chaque boss)
    Un dongeon est composée d'UNE salle de boss et d'UN shop exactement, il peuvent être situées nimporte ou sur la carte, même au spawn.
    Les dongeons sont composés d'une multitude de salles "classiques"
    Nous avons choisit de pousser l'aspect labyrinthique du jeu au maximum (tout en restant jouable pour tout type de joueur)
    Ainsi shop peut se trouver derrière une salle de boss et réciproquement.
    Vous pouvez arriver dans une salle sans que la porte précédenter ne soit présente mais avec d'autrs portes.
      NB: Il existe TOUJOURS un moyen pour retourner dans TOUTES les salles.
    Vous pouvez arriver dans une salle dont la porte que vous venez d'utiliser est fermée.
    Ainsi nous poussons le joueur à explorer tout le dongeon.
      Important, nous avons choisit de ne pas faire de salle dit boucle (à la inception) pour faciliter la correction.
      Même si l'idée d'une telle salle est une méchanique très intéressante.
    Le contenue d'une salle "classique" est générer aléatoirement, à savoir les monstres, les portes, les items, les obstacles,
    ainsi que les positions de ces derniers.
    Plus les salles sont éloignées du spawn, plus la difficulté augmente.
    PLus le joueur "monte dans les niveaux" et plus la variété de monstreres sera grande.
      NB: Les nouveux monstres ont de nouvelles caractéristiques et sont "plus forts".
    
  Nous avons choisit de séparer les salles en 3 catégories :
    - Les spawn avec pour le premier les commandes au sol puis des pentagrames pour les suivantes (Sans obstacles)
    - Les shops avec 3 items au sol, le prix indiqué et un pendu dans la salle                    (Sans obstacles)
      NB: Les portes de shop sont facilement identifiables car dorrées
    - Les salles de monstres :                                                                    (Avec obstacles)
      - Les salles "classique" avec des monstres, objets et obstacles
      - Les salles de boss, avec un boss, items et obstacles.
        NB: Les portes de boss sont facilement identifiables car grises avec un crâne
            Lorsque vous tuer un boss, une trape est générée au centre de la pièce pour changer de niveau
    
  
  Les monstres et boss et IA:
    Beaucoup de monstres et boss ont étés crées mais l'IA reste la partie la plus impréssionante.
    Pour l'IA:
    
    Pour les Monstres:
    
    Pour les Boss:
  
  Les items :
    Une multitude l'items à étée clée avec des caractéristiques spécifiques pour chacuns (Attention, il y a un malus dans le jeu !)
    Les caractéristiques de chacuns de ses items sont à retrouver dans le code pour ne pas surchager cette descition.
    Les effets ne sont pas aléatoirs.
    
    Le taux d'apparition d'items est volontairement très très imortant pour permettre de tous les utiliser à la correction.
    
    Items principeaux:
    - Les bombes : Permettent d'exploser les obstacles, font des dégats au hero et aux monstres
    - Les items de soins : Redonne des vies au heros
    - Les items pasifs : Donne un bonus / malus au hero pour le reste de l'aventure
    - Les pièces : Permettent d'acheter des items au shop (touche entrée)
  
  Les obstacles :
    - Les pièges : Il peuvent faire des dégats aux joueur toutes les x secondes.
    - Les roches : Elles bloquent un joueur et peuvent êtres cassés par les bombes et font apparaitre des objets en retour.
    
  Les portes :
    Les portes ne peuvent êtres ouvertes que lorsque l'intégralitée des monstres ont étés tués !
    - Les portes classiques : s'ouvrent quand il n'y a plus de monstres dans la salle.
    - Les portes de shop / bosse : ont les mêmes porpriétées que les classique mais une texture différente.
    - Les portes fermées à clé : S'ouvrent si le jouer à une clé et restent ouverte jusqu'à la fin du jeu.
      Ces portes ont aussi une texture spéciale
    
  Le HUD:
  
  Pour vous repérer dans le code et comprendre la construction: (avant de lire les commentaires du code)
    
    Au niveau de la génération:
      - Un level correspond à l'ensemble d'un dongeon (C'est à dire un spawn, un shop, des salles classiques et une salle de boss)
        Le level est composé de plusieurs GamesMap
        
      - Une GameMap : C'est une GameRoom avec ses coordonnées dans le level
      
      - Une GameRoom : C'est une salle de jeu avec tous ses attributs
 
 
 
 
 
 
 
 
 
 
 
 
