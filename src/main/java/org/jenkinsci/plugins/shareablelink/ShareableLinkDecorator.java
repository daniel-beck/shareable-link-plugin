package org.jenkinsci.plugins.shareablelink;

import hudson.Extension;
import hudson.model.PageDecorator;
import jenkins.model.Jenkins;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.DoNotUse;
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Extension
public class ShareableLinkDecorator extends PageDecorator {
    @Restricted(DoNotUse.class)
    public String getShareableUrl() {
        StaplerRequest r = Stapler.getCurrentRequest();

        List<Ancestor> ancestors = r.getAncestors();

        Ancestor last = null;
        if (ancestors.size() > 0) {
            last = ancestors.get(ancestors.size() - 1);
        }

        String rest = "";
        if (last != null) {
            rest = r.getRequestURIWithQueryString().substring(last.getUrl().length());
        }

        String canonicalPath = ThreadService.getUrlForObject(ancestors);

        if (canonicalPath.endsWith("/") && rest.startsWith("/")) {
            rest = rest.substring(1);
        }

        String result = Jenkins.getInstance().getRootUrl() + canonicalPath + rest;
        return result;
    }
}
