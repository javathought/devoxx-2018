package io.github.javathought.devoxx.dao;

import io.github.javathought.devoxx.model.Credentials;
import io.github.javathought.devoxx.model.Role;
import io.github.javathought.devoxx.model.User;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.javathought.devoxx.dao.Connexion.UUIDToBytes;
import static io.github.javathought.devoxx.db.Tables.ROLES;
import static io.github.javathought.devoxx.db.Tables.USERS;
import static io.github.javathought.devoxx.db.Tables.USER_ROLES;

/**
 *
 */
public class UsersDao {

    private static final Logger LOG = LoggerFactory.getLogger(UsersDao.class);
    private static Connection conn = Connexion.getInstance().getDbConnection();

    public static List<User> getAll() {
        return DSL.using(conn).
                fetch(USERS)
                .map(record -> mapUser(record));
    }

    public static Optional<User> getById(long id) {
        return DSL.using(conn).selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptional()
                .map(UsersDao::mapUser);
    }

    public static Optional<User> getByName(String name) {
        return DSL.using(conn).selectFrom(USERS)
                .where(USERS.NAME.eq(name))
                .fetchOptional()
                .map(UsersDao::mapUser);
    }

    public static void create(User user) {
        long id = DSL.using(conn).insertInto(USERS,
                USERS.UUID,
                USERS.NAME,
                USERS.PASSWORD
        )
                .values (
                        UUIDToBytes(UUID.randomUUID()),
                        user.getName(),
                        user.getKey()
                )
                .returning(USERS.ID).fetchOne().getId().intValue();

        user.setId(id);

        DSL.using(conn).insertInto(USER_ROLES,
                USER_ROLES.ROLE_ID, USER_ROLES.USER_ID)
                .values(2l, id).execute();

        updateAdminRights(user);
    }

    public static void update(User user) {
        if (user.getKey().isEmpty()) {
            DSL.using(conn).update(USERS)
                    .set(USERS.NAME, user.getName())
                    .where(USERS.ID.eq(user.getId()))
                    .execute();
        } else {
            DSL.using(conn).update(USERS)
                    .set(USERS.NAME, user.getName())
                    .set(USERS.PASSWORD, user.getKey())
                    .where(USERS.ID.eq(user.getId()))
                    .execute();
        }

        updateAdminRights(user);

    }

    private static void updateAdminRights(User user) {


        if (user.getRoles().contains(new Role(Role.ADMIN))) {
            boolean notAdmin = DSL.using(conn).selectFrom(USER_ROLES)
                    .where(USER_ROLES.ROLE_ID.eq(1l))
                    .and(USER_ROLES.USER_ID.eq(user.getId()))
                    .fetch().isEmpty();

            if (notAdmin) {
                DSL.using(conn).insertInto(USER_ROLES,
                    USER_ROLES.ROLE_ID, USER_ROLES.USER_ID)
                    .values(1l, user.getId()).execute();

            }

        } else {
            DSL.using(conn).deleteFrom(USER_ROLES)
                    .where(USER_ROLES.ROLE_ID.eq(1l))
                    .and(USER_ROLES.USER_ID.eq(user.getId()))
                    .execute();
        }
    }

    public static void delete(long id) {
        DSL.using(conn).delete(USERS)
                .where(USERS.ID.eq(id)).execute();
    }

    public static boolean authenticate(Credentials credentials) {

        /*
        Hack with :
        {
            "username": "admin",
            "password": "e' or '1' = '1"
        }
        */

        return ! DSL.using(conn)
                .fetch("SELECT 1 FROM devoxx_tia.users WHERE name = '" + credentials.getUsername()
                + "' AND password = '" + credentials.getPassword()
                        + "'").isEmpty();

/*
        return DSL.using(conn).selectFrom(USERS)
                .where(USERS.NAME.eq(credentials.getUsername())
                        .and(USERS.PASSWORD.eq(User.getHash(credentials.getPassword()))))
                .fetchOptional().isPresent();
*/
    }

    public static User mapUser(Record record) {
        User user = new User(record.get(USERS.ID),
                UUID.nameUUIDFromBytes(record.get(USERS.UUID)),
                record.get(USERS.NAME));
        DSL.using(conn).select().from(USER_ROLES)
                .join(ROLES).on(ROLES.ID.eq(USER_ROLES.ROLE_ID))
                .where(USER_ROLES.USER_ID.eq(user.getId()))
        .fetch().forEach(r -> user.getRoles().add(new Role(r.get(ROLES.NAME))));



        LOG.trace("user returned : {}", user);
        return user ;
    }

}
