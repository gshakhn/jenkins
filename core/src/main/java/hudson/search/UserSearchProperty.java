package hudson.search;

import hudson.Extension;
import hudson.model.User;
import hudson.model.UserProperty;
import hudson.model.UserPropertyDescriptor;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

public class UserSearchProperty extends hudson.model.UserProperty {
    
    private final boolean insensitiveSearch;

    public UserSearchProperty(boolean insensitiveSearch) {
        this.insensitiveSearch = insensitiveSearch;
    }

    @Exported
    public boolean getInsensitiveSearch() {
        return insensitiveSearch;
    }
    
    public static boolean isCaseSensitive(){
        User user = User.current();
        boolean caseSensitive = false;
        if(user!=null && user.getProperty(UserSearchProperty.class).getInsensitiveSearch()){//Searching for anonymous user is case-sensitive
          caseSensitive=true;
        }
        return caseSensitive;
    }
    

    @Extension
    public static final class DescriptorImpl extends UserPropertyDescriptor {
        public String getDisplayName() {
            return "Setting for search";
        }

        public UserProperty newInstance(User user) {
            return new UserSearchProperty(false); //default setting is case-sensitive searching
        }

        @Override
        public UserProperty newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new UserSearchProperty(formData.optBoolean("insensitiveSearch"));
        }

    }

}