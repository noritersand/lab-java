package jdk.java.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link Path} 클래스 테스트
 * <p>
 * src/main 혹은 src/test는 로컬에서만 사용해야하는 경로다. 이 경로들은 메이븐 개발 환경에서만 존재하는 폴더 구조이고 war나 jar로 빌드되면 존재하지 않는 경로이기 때문.
 * 따라서 경로빌드 후 생성될 경로를 절대 경로로 프로퍼티에서 관리하는 편이 좋다.
 *
 * @author fixalot
 * @since 2017-07-27
 */
@Slf4j
public class PathTest {

    @Test
    public void initialize() throws IOException {
        Path path = Paths.get("src/test/resources/path-test/amiexist.txt");
        Path path1 = Paths.get(URI.create("file://C:/project/workspace"));
        Path path2 = Paths.get("C:\\project\\workspace");
        Path path3 = Paths.get("localhost/upload");
        Path path4 = Paths.get("/localhost/upload");

        assertEquals("src\\test\\resources\\path-test\\amiexist.txt", path.toString());
        assertEquals("\\\\C\\project\\workspace", path1.toString());
        assertEquals("C:\\project\\workspace", path2.toString());
        assertEquals("localhost\\upload", path3.toString());
        assertEquals("localhost\\upload", path3.toString());
        assertEquals("\\localhost\\upload", path4.toString());
    }

    @Test
    public void testRelativize() {
        Path dev = new File("c:\\dev\\git").toPath();
        Path sso = new File("c:\\sso").toPath();
        assertEquals("..\\..\\sso", dev.relativize(sso).toString()); // dev에서 sso로 가려면 'cd ..\..\sso'
        assertEquals("..\\dev\\git", sso.relativize(dev).toString()); // sso에서 dev로 가려면 'cd ..\dev\git'
        assertEquals("", dev.relativize(dev).toString()); // dev에서 dev로 가려면 'cd .'
    }

    @Test
    public void testRelativize2() {
        Path source = new File("webapp\\upload\\temp\\").toPath();
        Path target = new File("webapp\\upload\\temp\\201612\\28201838255.png").toPath();
        assertEquals("201612\\28201838255.png", source.relativize(target).toString()); // dev에서 sso로 가려면 'cd ..\..\sso'
    }

    @Test
    public void testResolve() throws IOException {
        Path dev = new File("c:\\dev\\git").toPath();
        Path someFile = dev.resolve("someFile");
        assertEquals("c:\\dev\\git\\someFile", someFile.toString());
    }

    @Test
    public void shouldError() {
        // 없는 경로라도 단순 문자열이면 문제가 없지만
        Paths.get("c:", "\\ppp", "\\aaa");
        try {
            // file 프로토콜이 붙으면 파일이 없을때 에러난다.
            Paths.get("file://c:\\ppp");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidPathException);
        }
    }

    @Test
    public void getRoot() {
        log.debug("relative paths:");
        log.debug(Paths.get("/").toString()); // just "\"
        log.debug(Paths.get("").toString()); // ""
        log.debug(Paths.get("/").toFile().toString()); // just "\"
        log.debug(Paths.get("").toFile().toString()); // ""

        log.debug("absolute paths:");
        log.debug(Paths.get("/").toAbsolutePath().toString()); // just "\"
        log.debug(Paths.get("").toAbsolutePath().toString()); // ""
        log.debug(Paths.get("/").toAbsolutePath().toFile().toString()); // just "\"
        log.debug(Paths.get("").toAbsolutePath().toFile().toString()); // ""
    }
}
