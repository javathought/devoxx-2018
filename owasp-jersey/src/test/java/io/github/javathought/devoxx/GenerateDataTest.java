package io.github.javathought.devoxx;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.util.UUID;

import static io.github.javathought.devoxx.dao.Connexion.UUIDToBytes;

public class GenerateDataTest {

    @Test
    public void generate_uuid_hex() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Hex.encodeHexString(UUIDToBytes(UUID.randomUUID())));

        }
    }
}
