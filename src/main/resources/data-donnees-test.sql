INSERT INTO role (id, nom) VALUES
       (1, 'CLIENT'),
       (2, 'VENDEUR'),
       (3, 'ADMINISTRATEUR');

INSERT INTO utilisateur (id, email, password, role_id) VALUES
    (1, 'a@a', '$2a$10$tod199z/WlRRqwJ5NqWiNuMTMW3GgO.rDWQgRfmu9ZkzaAxzgmdtW' , 1),
    (2, 'b@b', '$2a$10$tod199z/WlRRqwJ5NqWiNuMTMW3GgO.rDWQgRfmu9ZkzaAxzgmdtW' , 2),
    (3, 'c@c', '$2a$10$tod199z/WlRRqwJ5NqWiNuMTMW3GgO.rDWQgRfmu9ZkzaAxzgmdtW' , 3);

INSERT INTO produit (nom, prix, vendeur_id) VALUES
    ('filament PLA', 25.99, NULL),
    ('buse 2mm', 50.20, 2);

INSERT INTO etiquette (nom) VALUES
        ('best seller'),
        ('promo');

INSERT INTO etiquette_produit (produit_id, etiquette_id) VALUES
        (1 , 1),
        (1 , 2),
        (2, 2);

