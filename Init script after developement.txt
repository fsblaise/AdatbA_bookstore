DECLARE
    schema_name VARCHAR(255);
    create_drop NUMBER;
    do_drop NUMBER;
BEGIN
    schema_name := 'C##';
    create_drop := 0;
    do_drop := 1;

    IF create_drop = 1 THEN
        IF do_drop = 1 THEN
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_OFFLINE cascade constraints';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_ONLINE cascade constraints';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_PRODUCT cascade constraints ';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_PURCHASE cascade constraints';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_PURCHASED_PRODUCTS cascade constraints';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_STOCK cascade constraints';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_STORE cascade constraints';
            execute immediate 'drop table ' || schema_name || '.BOOK_STORE_USERS cascade constraints';
        END IF;

        execute immediate 'create table ' || schema_name || '.BOOK_STORE_OFFLINE (id number(10,0) generated as identity, purchase_id number(10,0), store_id number(10,0), primary key (id))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_ONLINE (id number(10,0) generated as identity, address varchar2(20 char), dateTime date, purchase_id number(10,0), primary key (id))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_PRODUCT (id number(10,0) generated as identity, genre varchar2(20 char), name varchar2(100 char), production varchar2(4 char), review number(10,0), type varchar2(20 char), primary key (id))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_PURCHASE (id number(10,0) generated as identity, dateOfPurchase date, price number(10,0), review number(10,0), user_id number(10,0), primary key (id))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_PURCHASED_PRODUCTS (Purchase_id number(10,0) not null, products number(10,0))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_STOCK (id number(10,0) generated as identity, capacity number(10,0), isLow number(10,0) not null, sum number(10,0), product_id number(10,0), store_id number(10,0), primary key (id))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_STORE (id number(10,0) generated as identity, capacity number(10,0), coords varchar2(20 char), place varchar2(100 char), type varchar2(7 char), primary key (id))';
        execute immediate 'create table ' || schema_name || '.BOOK_STORE_USERS (id number(10,0) generated as identity, birthDate date, email varchar2(30 char), isRegular number(1,0), name varchar2(50 char), password varchar2(64 char), purchasedProducts number(10,0), primary key (id))';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_STORE add constraint UK_3y9mwugl2smlv4vp89q2oqptt unique (place)';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_OFFLINE add constraint FKfxlk1q4b0c8g9h5arul4i1mji foreign key (purchase_id) references C##AWR2BO.BOOK_STORE_PURCHASE';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_OFFLINE add constraint FK1ukq3jhlvagk31s5x0iycjwx foreign key (store_id) references C##AWR2BO.BOOK_STORE_STORE';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_ONLINE add constraint FKm2px7qea88y94tek2wuveq5ay foreign key (purchase_id) references C##AWR2BO.BOOK_STORE_PURCHASE';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_PURCHASE add constraint FK6dt5sj91ndg661l0vq9hbisfo foreign key (user_id) references C##AWR2BO.BOOK_STORE_USERS';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_PURCHASED_PRODUCTS add constraint FKfu4nx2mrkdup3fd2p1js0eqpg foreign key (Purchase_id) references C##AWR2BO.BOOK_STORE_PURCHASE';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_STOCK add constraint FKcyr44r1vwe7iumaw4ke107uof foreign key (product_id) references C##AWR2BO.BOOK_STORE_PRODUCT';
        execute immediate 'alter table ' || schema_name || '.BOOK_STORE_STOCK add constraint FKewlo5vi9aqpygvx1wkprqp2y0 foreign key (store_id) references C##AWR2BO.BOOK_STORE_STORE';
    END IF;
end;


INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Az utolsó mohikán', '1826', 'Kaland');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'A század legnagyobb hazugsága', '2007', 'Történelmi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A mester és Margarita', '1967', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Regény', 'Egri csillagok', '1899', 'Történelmi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Vuk', '1965', 'Gyerekkönyv');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'Az Élet Útjai', '2006', 'Önéletrajz');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Az aranyember', '1902', 'Történelmi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'A katedrális', '1989', 'Történelmi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Harry Potter és a bölcsek köve', '1997', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A Szél Neve', '2007', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Az alkimista', '1988', 'Spiritualitás');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Regény', 'Az éhezők viadala', '2008', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Könyök a sötétben', '1999', 'Krimi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A hobbit', '1937', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'A dzsungel könyve', '1894', 'Gyerekkönyv');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Momo', '1973', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'Gyalog galopp', '1965', 'Humor');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'A zöld íjász', '1885', 'Kaland');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Regény', 'A kis herceg', '1943', 'Filozófiai');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Egy darab szív', '2003', 'Romantikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'A gyűrűk ura', '1954', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Anna Karenina', '1878', 'Önéletrajz');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A szabadság ötven árnyalata', '2011', 'Erotika');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Regény', 'Az élet dolgairól', '1998', 'Önéletrajz');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Watchmen', '1986', 'drama');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Maus', '1991', 'haboru');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Saga', '2012', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Sandman', '1989', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'The Walking Dead', '2003', 'horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'V for Vendetta', '1982', 'politika');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Preacher', '1995', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Bone', '1991', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Sin City', '1991', 'noir');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Hellboy', '1993', 'akcio');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Akira', '1982', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'The Boys', '2006', 'akcio');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Fables', '2002', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Transmetropolitan', '1997', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Y: The Last Man', '2002', 'akcio');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'The Sandman: Overture', '2013', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Black Hole', '2005', 'drama');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Persepolis', '2000', 'haboru');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', '100 Bullets', '1999', 'noir');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'American Splendor', '1976', 'memoar');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Daytripper', '2010', 'drama');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Batman: Year One', '1987', 'akcio');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Batman: The Dark Knight Returns', '1986', 'akcio');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'The League of Extraordinary Gentlemen', '1999', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Marvels', '1994', 'superhos');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Spider-Man: Blue', '2002', 'superhos');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'The Umbrella Academy', '2007', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Batman: The Long Halloween', '1996', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Watchmen', '1986', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Sandman', '1989', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Preacher', '1995', 'humoros');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Hellboy', '1994', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'V for Vendetta', '1988', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'The Walking Dead', '2003', 'horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'The Boys', '2006', 'szatirikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Sin City', '1991', 'noir');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Saga', '2012', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Fables', '2002', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Y: The Last Man', '2002', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Transmetropolitan', '1997', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Akira', '1982', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Nausicaä of the Valley of the Wind', '1982', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Dragon Ball', '1984', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'One Piece', '1997', 'kaland');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Naruto', '1997', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Death Note', '2003', 'misztikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'Fullmetal Alchemist', '2001', 'fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Attack on Titan', '2009', 'horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Black Butler', '2006', 'misztikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Bleach', '2001', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'képregény', 'My Hero Academia', '2014', 'szuperhős');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'képregény', 'Assassination Classroom', '2012', 'humoros');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Tokyo Ghoul', '2011', 'horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'képregény', 'Batman: The Killing Joke', '1988', 'akcio');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'CD', 'Thriller', '1982', 'Pop');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'CD', 'Back in Black', '1980', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'A kis herceg', '1943', 'Filozofikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Az úr sötét anyaga', '1995', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A csendes amerikai', '1955', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Az idő rövid története', '1987', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A Szél neve', '2007', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Az alkimista', '1988', 'Spiritualis');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Az Oroszlán, a boszorkány és a ruhásszekrény', '1950', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Az éhezők viadala', '2008', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A bűnök szigete', '2003', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Harry Potter és a bölcsek köve', '1997', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'Az Egyik', '2014', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'A Pillangó-hatás', '1999', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'A bálna árnyéka', '1851', 'Klasszikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Szenvedélyek viharában', '1847', 'Klasszikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'Az ötödik hegy', '1995', 'Filozofikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'A szolgálólány meséje', '1985', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Az élet értelme', '1983', 'Filozofikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'A Rémálmok álma', '1978', 'Horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'Háború és béke', '1869', 'Klasszikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Az aranyszem', '1865', 'Klasszikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Regény', 'A pálforduló', '1975', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Regény', 'Körbejárás a Holdon', '1865', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Regény', 'Holtak naplója', '2006', 'Horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'CD', 'Nevermind', '1991', 'Grunge');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'CD', 'The Dark Side of the Moon', '1973', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'CD', 'Bad', '1987', 'Pop');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'CD', 'Californication', '1999', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'CD', 'Britney', '2001', 'Pop');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'CD', 'Ten', '1991', 'Grunge');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'CD', 'The Eminem Show', '2002', 'Hip Hop');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'CD', 'Appetite for Destruction', '1987', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'CD', 'Blood Sugar Sex Magik', '1991', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'CD', 'Back to Black', '2006', 'Soul');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'CD', 'Dangerously in Love', '2003', 'RnB');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'CD', 'Legend', '1984', 'Reggae');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'CD', 'In Utero', '1993', 'Grunge');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'CD', 'The Joshua Tree', '1987', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'CD', 'Hotel California', '1976', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'CD', 'Sgt. Peppers Lonely Hearts Club Band', '1967', 'Rock');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'CD', 'Oops!... I Did It Again', '2000', 'Pop');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Harry Potter és a bölcsek köve', '2001', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'Az idő urai', '2005', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'Az élet, amit megadtak nekem', '2018', 'Életrajzi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Hangoskönyv', 'Az én XX. századom', '2016', 'Memoár');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A testőr', '1999', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A mélység hangjai', '2009', 'Krimi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'Az emberi lét igazságosztója', '2017', 'Filozófiai');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Az ördög színháza', '2012', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'Az alkimista', '1988', 'Kalandregény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A százezer király csatája', '2013', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A sötétség ötven árnyalata', '2011', 'Erotikus');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'Az elszabaduló autók', '2015', 'Kalandregény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Hangoskönyv', 'Az időfutár', '2014', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Az utolsó kívánság', '1993', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A szél neve', '1985', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A falka', '2020', 'Krimi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Az élet titkai', '2016', 'Önismereti');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Hangoskönyv', 'A tűz jegyében', '2008', 'Történelmi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'Az állatfarm', '1945', 'Politikai');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A négy egykerekű', '1961', 'Gyermekregény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A holtak szava', '2006', 'Horror');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A katedrális', '2010', 'Történelmi regény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A vonzerő ereje', '2006', 'Önismereti');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Az elveszett kód', '2009', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A szent grál', '2003', 'Történelmi regény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Hangoskönyv', 'A belső hang', '2018', 'Önismereti');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Az igazság pillanata', '2011', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A hópárduc fogságában', '2014', 'Kalandregény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A bíbor trón', '2005', 'Történelmi regény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A szörnyeteg árnyéka', '2016', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A boldogság választása', '2012', 'Önismereti');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A bosszú ára', '2010', 'Krimi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Hangoskönyv', 'A megvilágosodás útja', '2008', 'Önismereti');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'Az idő urai', '2013', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A rettegés foka', '2009', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A vágy színhelyei', '2015', 'Erotika');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'A dzsungel törvénye', '2006', 'Kalandregény');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'Az igazság kardja', '2012', 'Fantasy');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(3, 'Hangoskönyv', 'A belső bölcsesség', '2010', 'Önismereti');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(4, 'Hangoskönyv', 'A halál hívása', '2008', 'Thriller');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(5, 'Hangoskönyv', 'Az elveszett idő nyomában', '2005', 'Sci-fi');
INSERT INTO BOOK_STORE_PRODUCT(review, type, name, production, genre) VALUES(2, 'Hangoskönyv', 'A bennünk rejlő erő', '2017', 'Önismereti');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Budapest, IX. kerület, Üllői út 182.', 750, '534.0|287.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Debrecen, Nagyállomás utca 1.', 400, '940.0|261.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Szeged, Tisza Lajos krt. 49.', 200, '695.0|544.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Győr, Budai út 1.', 1000, '300.0|236.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Pécs, Széchenyi tér 1.', 500, '380.0|598.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Debrecen, Virág utca 10.', 400, '940.0|241.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Szeged, Csap utca 90.', 600, '705.0|534.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Csanádpalota, Hattyú utca 28.', 100, '786.0|556.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Makó, Szegedi utca 22.', 300, '744.0|564.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Vásárhely, Kiskakas út 3.', 600, '726.0|530.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'fizikai', 'Eger, Ideges sor 67.', 500, '735.0|196.0');
INSERT INTO BOOK_STORE_STORE(type, place, capacity, coords) VALUES( 'online', 'online', 10.000, '0|0');
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kovács János', 'kovacs.janos@nevhu.com', TO_DATE('20-05-1975','dd mm yyyy'), 11, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Nagy Anna', 'nagy.anna@nevhu.com', TO_DATE('07-09-1983', 'dd mm yyyy'),16, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Tóth Gábor', 'toth.gabor@nevhu.com', TO_DATE('11-12-1972','dd mm yyyy'), 22, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Szabó Eszter', 'szabo.eszter@nevhu.com', TO_DATE('29-07-1965', 'dd mm yyyy'),6, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kiss László', 'kiss.laszlo@nevhu.com', TO_DATE('14-02-1990','dd mm yyyy'), 9, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Horváth Mária', 'horvath.maria@nevhu.com',TO_DATE( '23-11-1987','dd mm yyyy'), 2, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Farkas Péter', 'farkas.peter@nevhu.com', TO_DATE('03-08-1964','dd mm yyyy'), 12, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Szűcs Judit', 'szucs.judit@nevhu.com', TO_DATE('28-04-1981','dd mm yyyy'), 25, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Varga Zsolt', 'varga.zsolt@nevhu.com', TO_DATE('01-06-1978', 'dd mm yyyy'),21, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kerekes Éva', 'kerekes.eva@nevhu.com',TO_DATE( '12-03-1971','dd mm yyyy'), 27, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Balogh Béla', 'balogh.bela@nevhu.com', TO_DATE('18-09-1995', 'dd mm yyyy'),15, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Simon Andrea', 'simon.andrea@nevhu.com', TO_DATE('30-12-1973','dd mm yyyy'), 29, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Takács Csaba', 'takacs.csaba@nevhu.com', TO_DATE('06-07-1989','dd mm yyyy'), 23, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Fehér Zoltán', 'feher.zoltan@nevhu.com', TO_DATE('17-11-1979','dd mm yyyy'), 20, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Szőke Ildikó', 'szoke.ildiko@nevhu.com', TO_DATE('29-02-1968','dd mm yyyy'), 3, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Papp Dániel', 'papp.daniel@nevhu.com', TO_DATE('03-05-1992','dd mm yyyy'), 1, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Gáspár Zsuzsanna', 'gaspar.zsuzsanna@nevhu.com', TO_DATE('19-09-1986','dd mm yyyy'), 28, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Bíró István', 'biro.istvan@nevhu.com', TO_DATE('11-04-1980','dd mm yyyy'), 10, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Antal Adrienn', 'antal.adrienn@nevhu.com', TO_DATE('24-08-1976', 'dd mm yyyy'),19, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Fodor József', 'fodor.jozsef@nevhu.com', TO_DATE('07-01-1998', 'dd mm yyyy'),14, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Bálint Zsófia', 'balint.zsofia@valami.hu',TO_DATE( '11-09-1988', 'dd mm yyyy'),22, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Csont Dániel', 'csont.daniel@valami.hu', TO_DATE('07-05-1996', 'dd mm yyyy'),12, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kovács László', 'kovacs.laszlo@valami.hu', TO_DATE('02-03-1981', 'dd mm yyyy'),16, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Budai István', 'budai.istvan@valami.hu', TO_DATE('24-12-1975', 'dd mm yyyy'),7, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Molnár Katalin', 'molnar.katalin@valami.hu', TO_DATE('13-07-1972', 'dd mm yyyy'),25, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Nagy Tamás', 'nagy.tamas@valami.hu', TO_DATE('18-02-1985', 'dd mm yyyy'),11, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Horváth Károly', 'horvath.karoly@valami.hu', TO_DATE('05-11-1990', 'dd mm yyyy'),19, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Pintér Anna', 'pinter.anna@valami.hu',TO_DATE( '08-08-1989', 'dd mm yyyy'),4, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kiss Gergely', 'kiss.gergely@valami.hu', TO_DATE('19-04-1993', 'dd mm yyyy'),28, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Farkas Levente', 'farkas.levente@valami.hu', TO_DATE('01-01-1980','dd mm yyyy'), 13, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Tóth Kinga', 'toth.kinga@valami.hu', TO_DATE('27-06-1998', 'dd mm yyyy'),10, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kertész Zsolt', 'kertesz.zsolt@valami.hu', TO_DATE('10-12-1978', 'dd mm yyyy'),3, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Simon Máté', 'simon.mate@valami.hu', TO_DATE( '23-02-1971', 'dd mm yyyy'),24, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kiss Szilveszter', 'kiss.szilveszter@valami.hu', TO_DATE( '14-05-1977', 'dd mm yyyy'),12, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Fehér Krisztina', 'feher.krisztina@valami.hu', TO_DATE('14-11-1991','dd mm yyyy'), 5, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Balogh Balázs', 'balogh.balazs@valami.hu', TO_DATE('30-08-1997','dd mm yyyy'), 20, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Kis Ágnes', 'kis.agnes@valami.hu', TO_DATE('06-05-1977', 'dd mm yyyy'),15, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Németh András', 'nemeth.andras@valami.hu', TO_DATE('11-03-1983', 'dd mm yyyy'),23, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Takács Zsuzsanna', 'takacs.zsuzsanna@valami.hu', TO_DATE('17-12-1987', 'dd mm yyyy'),9, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('e6837af0b5dcb5397c6bc8b52c0fb95aa49a3fea4303e274c7501f0857cb9b80', 'Pálfi Viktória', 'palfi.viktoria@valami.hu', TO_DATE('20-02-1995', 'dd mm yyyy'),27, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 'admin', TO_DATE('26-04-2023', 'dd mm yyyy'),0, 0);
INSERT INTO BOOK_STORE_USERS(password, name, email, birthdate, purchasedproducts, isregular) VALUES('cf43e029efe6476e1f7f84691f89c876818610c2eaeaeb881103790a48745b82', 'alma', 'alma@alma.com', TO_DATE('26-04-2023', 'dd mm yyyy'),8, 0);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('02-01-2023','dd mm yyyy'), 55000, 4, 15);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('05-01-2023','dd mm yyyy'), 82000, 3, 23);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('10-01-2023','dd mm yyyy'), 45000, 2, 9);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('11-01-2023','dd mm yyyy'), 30000, 5, 19);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('15-01-2023','dd mm yyyy'), 68000, 4, 36);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('20-01-2023','dd mm yyyy'), 74000, 2, 4);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-01-2023','dd mm yyyy'), 6000, 1, 32);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('25-01-2023','dd mm yyyy'), 94000, 3, 12);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('30-01-2023','dd mm yyyy'), 15000, 5, 28);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('01-02-2023','dd mm yyyy'), 45000, 4, 10);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('03-02-2023','dd mm yyyy'), 80000, 2, 37);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('06-02-2023','dd mm yyyy'), 22000, 3, 21);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('09-02-2023','dd mm yyyy'), 66000, 1, 8);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('12-02-2023','dd mm yyyy'), 51000, 5, 31);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('15-02-2023','dd mm yyyy'), 94000, 4, 14);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('18-02-2023','dd mm yyyy'), 3000, 2, 25);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('20-02-2023','dd mm yyyy'), 88000, 3, 6);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('24-02-2023','dd mm yyyy'), 41000, 5, 17);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('28-02-2023','dd mm yyyy'), 75000, 1, 39);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('03-03-2023','dd mm yyyy'), 92000, 4, 29);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('06-03-2023','dd mm yyyy'), 13000, 2, 2);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('08-03-2023','dd mm yyyy'), 65000, 3, 35);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('11-03-2023','dd mm yyyy'), 55000, 4, 11);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('14-03-2023','dd mm yyyy'), 78000, 5, 26);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('17-03-2023','dd mm yyyy'), 27000, 1, 3);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 99000, 2, 20);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id) VALUES(TO_DATE('22-03-2023','dd mm yyyy'), 1000, 5, 42);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(1, 72);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(1, 27);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(1, 155);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(1, 41);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(1, 113);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(1, 6);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(2, 82);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(2, 20);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(2, 137);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(2, 97);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(2, 7);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(2, 118);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(3, 159);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(3, 71);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(3, 85);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(3, 144);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(3, 45);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(3, 130);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(4, 93);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(4, 103);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(4, 13);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(4, 137);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(4, 112);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(4, 33);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(5, 31);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(5, 141);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(5, 64);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(5, 119);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(5, 83);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(5, 155);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(6, 111);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(6, 65);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(6, 131);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(6, 28);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(6, 157);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(6, 6);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(7, 35);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(7, 76);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(7, 88);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(7, 134);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(7, 2);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(7, 95);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(8, 102);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(8, 13);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(8, 61);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(8, 153);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(8, 72);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(8, 86);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(9, 50);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(9, 151);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(9, 116);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(9, 21);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(9, 143);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(9, 99);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(10, 156);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(10, 42);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(10, 132);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(10, 79);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(10, 58);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(10, 25);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(11, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(11, 137);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(11, 103);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(11, 69);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(11, 41);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(11, 87);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(12, 114);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(12, 95);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(12, 12);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(12, 151);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(13, 3);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(13, 87);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(13, 105);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(13, 61);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(13, 34);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(13, 117);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(14, 156);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(14, 36);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(14, 81);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(14, 111);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(14, 98);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(14, 48);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(15, 11);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(15, 155);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(15, 107);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(15, 31);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(15, 130);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(15, 16);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(16, 22);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(16, 89);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(16, 15);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(16, 13);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(16, 11);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(16, 2);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(27, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(28, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(29, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(30, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(31, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(32, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(33, 1);
INSERT INTO BOOK_STORE_PURCHASED_PRODUCTS(purchase_id, products) VALUES(34, 1);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 50, 0, 1, 1);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 1, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 200, 0, 2, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 3, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 4, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 5, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 6, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 7, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 8, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 9, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 10, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 11, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 12, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 13, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 14, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 15, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 16, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 17, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 18, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 19, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 20, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 21, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 22, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 23, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 24, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 25, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 26, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 27, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 28, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 29, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 30, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 41, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 42, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 43, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 44, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 45, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 46, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 47, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 48, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 49, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 50, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 61, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 62, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 63, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 64, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 65, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 66, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 67, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 68, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 69, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 70, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 81, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 82, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 83, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 84, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 85, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 86, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 87, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 88, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 89, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 90, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 91, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 92, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 93, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 94, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 95, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 96, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 97, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 98, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 99, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 100, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(100, 100, 0, 101, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 102, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 103, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 104, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 105, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 106, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 107, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 108, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 109, 2);
INSERT INTO BOOK_STORE_STOCK(capacity, sum, ISLOW, PRODUCT_ID, STORE_ID) VALUES(200, 100, 0, 110, 2);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 27);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 28);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 29);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 30);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 31);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 32);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 33);
INSERT INTO BOOK_STORE_ONLINE(ADDRESS, DATETIME, PURCHASE_ID) VALUES('Szeged Alma u. 10', TO_DATE('22-03-2023','dd mm yyyy'), 34);

CREATE OR REPLACE PROCEDURE decrementStock(storeId IN NUMBER, productId IN NUMBER)
    IS
BEGIN
    UPDATE BOOK_STORE_STOCK SET SUM = SUM - 1 WHERE STORE_ID = storeId AND PRODUCT_ID = productId AND SUM > 0;
end;

CREATE OR REPLACE TRIGGER updateStock
    AFTER INSERT
    ON BOOK_STORE_OFFLINE
    FOR EACH ROW
DECLARE
    CURSOR purchases IS SELECT *
                        FROM BOOK_STORE_PURCHASED_PRODUCTS;
BEGIN
    FOR purchase IN purchases
        LOOP
            IF purchase.PURCHASE_ID = :NEW.PURCHASE_ID THEN
                decrementStock(:NEW.STORE_ID, purchase.PRODUCTS);
            end if;
        END LOOP;
end;

CREATE OR REPLACE PROCEDURE upgradeToRegular(userId IN NUMBER)
    IS
    counter NUMBER;
BEGIN
    SELECT COUNT(*) INTO counter FROM BOOK_STORE_PURCHASE WHERE USER_ID = userId;

    IF counter >= 10 THEN
        UPDATE BOOK_STORE_USERS t SET t.ISREGULAR = 1 WHERE t.ID = userId;
    end if;
end;

CREATE OR REPLACE VIEW BOOK_STORE_VW_PURCHASE AS
SELECT DATEOFPURCHASE, PRICE, REVIEW, USER_ID
FROM BOOK_STORE_PURCHASE;

CREATE OR REPLACE TRIGGER upgradeToRegular
    INSTEAD OF INSERT
    ON BOOK_STORE_VW_PURCHASE
    FOR EACH ROW
DECLARE
    counter NUMBER;
BEGIN
    INSERT INTO BOOK_STORE_PURCHASE(dateofpurchase, price, review, user_id)
    VALUES (:NEW.DATEOFPURCHASE, :NEW.PRICE, :NEW.REVIEW, :NEW.USER_ID);

    upgradeToRegular(:NEW.USER_ID);
end;

CREATE OR REPLACE TRIGGER updatePurchases
    AFTER INSERT
    ON BOOK_STORE_PURCHASE
    FOR EACH ROW
DECLARE
    user_id BOOK_STORE_USERS.ID%type;
BEGIN
    user_id := :NEW.USER_ID;
    UPDATE BOOK_STORE_USERS SET PURCHASEDPRODUCTS = PURCHASEDPRODUCTS + 1 WHERE USER_ID = user_id;
END;

CREATE OR REPLACE PROCEDURE deleteStore(storeId IN NUMBER) IS
    name BOOK_STORE_STORE.PLACE%type;
    id BOOK_STORE_STORE.ID%type;
BEGIN
    SELECT PLACE INTO name FROM BOOK_STORE_STORE WHERE ID = storeId;
    SELECT ID INTO id FROM BOOK_STORE_STORE WHERE PLACE = 'online';

    IF name <> 'online' THEN
        UPDATE BOOK_STORE_OFFLINE SET STORE_ID = id WHERE ID = storeId;
        DELETE FROM BOOK_STORE_STOCK WHERE STORE_ID = storeId;
        DELETE FROM BOOK_STORE_STORE WHERE ID = storeId;
    END IF;
END;

CREATE OR REPLACE PROCEDURE deleteProduct(product IN NUMBER) IS
BEGIN
    DELETE FROM BOOK_STORE_STOCK WHERE PRODUCT_ID = product;
    DELETE FROM BOOK_STORE_PRODUCT WHERE ID = product;
END;
