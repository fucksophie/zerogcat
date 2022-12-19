package lv.yourfriend.zerogcat.utils;

import java.nio.ByteBuffer;
import java.nio.file.Path;

import org.lmdbjava.CursorIterable;
import org.lmdbjava.Dbi;
import org.lmdbjava.Env;
import org.lmdbjava.KeyRange;
import org.lmdbjava.Txn;

import static org.lmdbjava.Env.create;
import static org.lmdbjava.DbiFlags.MDB_CREATE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.ByteBuffer.allocateDirect;

public class LMDBWrapper {
    public Path path;

    private Dbi<ByteBuffer> db;
    private Env<ByteBuffer> env;
    private Txn<ByteBuffer> tx;

    public LMDBWrapper(Path pathz, String name) {
        path = pathz;

        path.toFile().mkdirs();

        env = create()
            .setMapSize(10_485_760)
            .setMaxDbs(1)
            .open(path.toFile());

        db = env.openDbi(name, MDB_CREATE);

    }

    public void Destroy() {
        env.close();
    }

    public void Set(String key, String val) {
        db.put(sktbk(key), svtvk(val));
    }

    public String Get(String key) {
        String value = null;

        try (Txn<ByteBuffer> txn = env.txnRead()) { // Autoclose with TryResources, does not need .clean.
            final ByteBuffer found = db.get(txn, sktbk(key));
            if(found != null) {
                value = UTF_8.decode(txn.val()).toString();
            }
        }

        return value;
    }

    public String bts(ByteBuffer k) {
        return UTF_8.decode(k).toString();
    }

    public ByteBuffer sktbk(String k) {
        return allocateDirect(env.getMaxKeySize()).put(k.getBytes(UTF_8)).flip();
    }

    public ByteBuffer svtvk(String v) {
        return allocateDirect(v.length()*2).put(v.getBytes(UTF_8)).flip();
    }

    public CursorIterable<ByteBuffer> Entries() { // No autoclose, needs .clean
        tx = env.txnWrite();
        return db.iterate(tx, KeyRange.all());
    }

    public void Clean() {
        tx.close();
    }

    public Boolean Delete(String key) {
        return db.delete(sktbk(key));
    }
}
