package org.jenkinsci.plugins.shareablelink;

import hudson.model.ModelObject;
import org.kohsuke.stapler.Ancestor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.*;

public class ThreadService {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static class UrlInfo {
        public final int index;
        public final String path;

        public UrlInfo(int index, String path) {
            this.index = index;
            this.path = path;
        }
    }

    public static String getUrlForObject(final List<Ancestor> ancestors) {
        Future<String> result = executorService.submit(new Callable<String>() {
            public String call() throws Exception {
                String url = null;
                int index = 0;
                for (Ancestor ancestor : ancestors) {
                    try {
                        Object o = ancestor.getObject();
                        Method m = o.getClass().getMethod("getUrl");
                        url = m.invoke(ancestor.getObject()).toString();
                        index++;
                    } catch (NoSuchMethodException e) {
                        // no op
                    } catch (InvocationTargetException e) {
                        // no op
                    } catch (IllegalAccessException e) {
                        // no op
                    }
                }
                return url;
            }
        });
        try {
            return result.get();
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            return null;
        }
    }
}
