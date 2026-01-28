import ecs.Entity;
import ecs.EntityManager;
import ecs.PrefabRegistry;
import ecs.components.*;
import ecs.systems.MovementSystem;
import ecs.systems.OutputSystem;
import ecs.systems.PrefabSystem;

public class Game {

    private EntityManager em;
    private PrefabRegistry pf;
    private int frames;

    public Game() {
        pf = new PrefabRegistry();
        em = new EntityManager(pf);
        frames = 0;
        init();
    }

    public void update() {
        frames++;
        em.update(0);

        if(frames % 10 == 0) {
            em.getEntitiesWithComponents(PrefabLink.class).forEach(e -> {
                e.getComponent(PrefabLink.class).ready = true;
            });
        }
    }

    private void init() {
        try {
            pf.loadPrefabs("file:src/main/java/ecs/Prefabs.json");
            em.createEntity(pf.get("test"));
        } catch(Exception e) {
            System.err.println(e);
        }


        em.addSystem(new OutputSystem());
        em.addSystem(new PrefabSystem());
//        em.addSystem(new MovementSystem());

//        OutputSystem s = new OutputSystem();
//        em.addSystem(s);
    }
}
