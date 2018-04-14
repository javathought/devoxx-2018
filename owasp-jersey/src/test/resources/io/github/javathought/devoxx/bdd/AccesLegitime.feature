#language: fr

  Fonctionnalité: Accès légitime au serveur

    Contexte: L'utilisateur est légitime
      Quand l'utilisateur se connecte avec l'identifiant "legitime"
      Et l'utilisateur a le role "read"


    Scénario:
      Quand l'utilisateur appelle l'url "myresource"
      Alors le code retour est 200
        Et la réponse contient "Got it!"