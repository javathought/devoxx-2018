#language: fr
  
  Fonctionnalité: En tant qu'administrateur, je veux pouvoir me connecter à l'ihm de l'application avec mon compte
    et mon mot de passe

  Scénario: les informations d'identification sont correctes
    Etant donné l'url de l'application "https://localhost:9090/#/login"
    Quand je me connecte avec le compte "admin" et le mot de passe "e' or '1'='1"
#    Quand je me connecte avec le compte "admin" et le mot de passe "admin"
    Alors la connexion est acceptée
#    Et je ferme le navigateur

  Scénario: les informations d'identification sont erronées
    Etant donné l'url de l'application "https://localhost:9090/#/login"
    Quand je me connecte avec le compte "admin" et le mot de passe "badpwd"
    Alors la connexion est refusée
#    Et je ferme le navigateur
    