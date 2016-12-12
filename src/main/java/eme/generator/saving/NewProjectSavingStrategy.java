package eme.generator.saving;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * Saving strategy that creates a new project for very saved model.
 * @author Timur Saglam
 */
public class NewProjectSavingStrategy extends AbstractSavingStrategy {
    private String projectName;

    /**
     * Basic constructor.
     */
    public NewProjectSavingStrategy() {
        super(true);
    }

    /*
     * @see eme.generator.saving.AbstractSavingStrategy#beforeSaving()
     */
    @Override
    protected void beforeSaving(String projectName) {
        this.projectName = projectName;
        // TODO (HIGH) Create new empty EMF project called (projectName)Model
    }

    /*
     * @see eme.generator.saving.AbstractSavingStrategy#filePath()
     */
    @Override
    protected String filePath() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        return workspace.getRoot().getLocation().toFile().getPath() + "/" + projectName + "Model/model/";
    }

    /*
     * @see eme.generator.saving.AbstractSavingStrategy#fileName()
     */
    @Override
    protected String fileName() {
        return projectName;
    }

}