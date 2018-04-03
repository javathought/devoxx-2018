insert into devoxx_tia.roles (uuid, name, ui_role) values
  (x'cc934b07231a4f9891befcc09d6ad7ef', 'super', 'Administrateur'),
  (x'016a6c39389948939b004f0d41920980', 'user', 'Utilisateur');

insert into devoxx_tia.users (uuid, name, password) values
(x'59ece0a2b5ad454ba934c370330b6534', 'admin', 'sha1:64000:18:JNRTgH/TQhBoO73HZo/sSbOpxyBZLq+p:h7bzkl27X1ctg9FzNSDtjKv6'),
(x'b9288b33ce4b414eb380828e6e2cbb64', 'normal', 'sha1:64000:18:ENMMiVqUamOLnNKd8Nmm0cv1arbVspeJ:2kGiLKMQn2wxM3a/ZMWkImdW'),
(x'9B5CD138772449CEB42744D1B05457B3', 'john.doe@example.com', 'cbfdac6008f9cab4083784cbd1874f76618d2a97'),
(x'12e7414f1b924ba7ae4ee48db05f6d63', 'demo@example.com', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3'),
(x'39ac5775fab3485a8abd94380ba9fe85', 'jane.doe@example.com', '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
(x'2849585a45164857a351ef119fa26b17', 'mike.doe@example.com', '315f166c5aca63a157f7d41007675cb44a948b33'),
(x'8bff18edc2534950afea005c98fca49b', 'arthur.doe@example.com', '21bd12dc183f740ee76f27b78eb39c8ad972a757')
;

601ff284d1e84214bc6847e15d74b24f

insert into devoxx_tia.user_roles values
  (1, 1), (1,2),
  (2,2),
  (3,2),
  (4,2),
  (5,2),
  (6,2), (6,1),
  (7,2)
  ;


