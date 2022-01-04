package ncu.cc.digger.models.user;

public class CurrentScene {
    private int sceneId;
    private String name;
    private String role;

    public CurrentScene() {
    }

    public CurrentScene(int sceneId, String name, String role) {
        this.sceneId = sceneId;
        this.name = name;
        this.role = role;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
