package io.github.javathought.devoxx.dao;

import io.github.javathought.devoxx.model.Todo;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.javathought.devoxx.dao.Connexion.UUIDToBytes;
import static io.github.javathought.devoxx.db.Tables.TODOS;
import static org.jooq.tools.StringUtils.defaultIfNull;

/**
 *
 */
public class TodosDao {

    private static final Logger LOG = LoggerFactory.getLogger(TodosDao.class);
    private static Connection conn = Connexion.getInstance().getDbConnection();

    public static List<Todo> getAll() {
        return DSL.using(conn).
                fetch(TODOS)
                .map(record -> mapTodo(record));
    }

    public static List<Todo> getPublic() {
        return DSL.using(conn).
                selectFrom(TODOS).where(TODOS.PUBLIC.eq((byte)1)).fetch()
                .map(record -> mapTodo(record));
    }

    public static Optional<Todo> getById(long id) {
        return DSL.using(conn).selectFrom(TODOS)
                .where(TODOS.ID.eq(id))
//                .where(TODOS.UUID.eq(UUIDToBytes(todo.getUuid())))
                .fetchOptional()
                .map(TodosDao::mapTodo);
    }

    public static void create(Todo todo) {
        long id = DSL.using(conn).insertInto(TODOS,
                TODOS.UUID,
                TODOS.USER_ID,
                TODOS.SUMMARY,
                TODOS.DESCRIPTION,
                TODOS.PUBLIC
        )
                .values(
                        UUIDToBytes(UUID.randomUUID()),
                        todo.getUserId(),
                        todo.getSummary(),
                        todo.getDescription(),
                        (byte) (todo.getPublique() ? 1 : 0)

                )
                .returning(TODOS.ID).fetchOne().getId().intValue();

        todo.setId(id);
    }

    public static void update(Todo todo) {
        DSL.using(conn).update(TODOS)
                .set(TODOS.SUMMARY, todo.getSummary())
                .set(TODOS.DESCRIPTION, todo.getDescription())
                .set(TODOS.PUBLIC, (byte) (todo.getPublique() ? 1 : 0))
                .where(TODOS.ID.eq(todo.getId()))
//                    .where(TODOS.UUID.eq(UUIDToBytes(todo.getUuid())))
                .execute();
    }

    public static void delete(long id) {
        DSL.using(conn).delete(TODOS)
                .where(TODOS.ID.eq(id))
//                .where(TODOS.UUID.eq(UUIDToBytes(uuid)))
                .execute();
    }

    public static List<Todo> getByOwner(long userId) {
        return DSL.using(conn).selectFrom(TODOS)
                .where(TODOS.USER_ID.eq(userId))
                .fetch()
                .map(TodosDao::mapTodo);
    }

    public static Todo mapTodo(Record record) {
        Todo todo = new Todo.TodoBuilder()
                .setId(record.get(TODOS.ID))
                .setUuid(UUID.nameUUIDFromBytes(record.get(TODOS.UUID)))
                .setUserId(defaultIfNull(record.get(TODOS.USER_ID), 0L))
                .setSummary(record.get(TODOS.SUMMARY))
                .setDescription(record.get(TODOS.DESCRIPTION))
                .setPublique(record.get(TODOS.PUBLIC).equals((byte)1) ? true : false)
                .build();
        LOG.trace("todo returned : {}", todo);
        return todo;
    }

}
