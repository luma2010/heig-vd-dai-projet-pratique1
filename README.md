# heig-vd-dai-projet-pratique1
#### Delétraz Alexandre - Chollet Florian

## Introduction
Le projet que nous avons choisi de faire permet d'ouvrir un fichier et d'opérer sur les variables qu'il contient.  
Les différentes opérations que l'application peut faire sont :
- Add, qui permet d'ajouter une variable.
- Delete qui permet de supprimer entièrement une variable.
- Modify qui permet de modifier la valeur de la variable.
- Rename qui permet de renommer une variable.

## Implémentation
Chacune des commandes ont été créés dans un fichier séparé dans le dossier commands.
Les commandes font attentions aux points suivants :
- La commande Add vérifie que la variable que nous souhaitons ajouter n'existe pas déjà. Auquel cas un message
  avertissant l'utilisateur s'affiche et le fichier n'est pas modifié.
- La commande Rename vérifie que le nouveau de la variable ne soit pas déjà prit par une autre variable dans le fichier.
  Auquel cas le fichier n'est pas modifié.
- La commande Delete vérifie que le nom de la variable existe dans le fichier. Si la variable n'existe pas, un message
  s'affiche indiquant le problème et le fichier n'est pas modifié.
- La commande Modify permet de modifier la valeur d'une variable.  

## Exemple d'utilisation

#### Add
La commande Add doit être utilisée comme ci-dessous :

```text
$ java -jar heig-vd-dai-projet-pratique-1.0-SNAPSHOT.jar <nomFichier> Add <nomVariable> <valeur>
```

Par exemple :
```text
$ java -jar heig-vd-dai-projet-pratique-1.0-SNAPSHOT.jar test.txt Add test 123
```
En cas de réussite le résultat doit être :

```text
Write new variable test with value 123
```

Si la variable existe déjà, le message doit être le suivant :

```text
Warning, variable test Already exist
```

#### Delete
La commande Delete doit être utilisée comme suit :

```text
java -jar heig-vd-dai-projet-pratique1-1.0-SNAPSHOT.jar <nomFichier> Delete <nomVariable>
```

Exemple :
```text
java -jar heig-vd-dai-projet-pratique1-1.0-SNAPSHOT.jar test.txt Delete test
```

Cette commande ne fourni pas de message en cas de réussite ou d'échec

#### Rename

L'utilisation de la commande Rename doit se faire comme l'exemple ci-dessous :

```text
java -jar heig-vd-dai-projet-pratique1-1.0-SNAPSHOT.jar test.txt Rename test newTest
```





