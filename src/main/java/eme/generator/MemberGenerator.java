package eme.generator;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;

import eme.model.ExtractedMethod;
import eme.model.ExtractedType;
import eme.model.datatypes.ExtractedAttribute;
import eme.model.datatypes.ExtractedDataType;
import eme.model.datatypes.ExtractedParameter;

/**
 * Generator class for Ecore members ({@link EOperation}s and {@link EStructuralFeature}s).
 * @author Timur Saglam
 */
public class MemberGenerator {
    private final Map<String, EClassifier> eClassifierMap;
    private final EcoreFactory ecoreFactory;
    private final SelectionHelper selector;
    private final EDataTypeGenerator typeGenerator;

    /**
     * Basic constructor.
     * @param typeGenerator is the {@link EDataTypeGenerator} instance.
     * @param selector is the {@link SelectionHelper} instance.
     * @param eClassifierMap is the map of already generated {@link EClassifier}s.
     */
    public MemberGenerator(EDataTypeGenerator typeGenerator, SelectionHelper selector, Map<String, EClassifier> eClassifierMap) {
        this.typeGenerator = typeGenerator;
        this.selector = selector;
        this.eClassifierMap = eClassifierMap;
        ecoreFactory = EcoreFactory.eINSTANCE;
    }

    /**
     * Adds the attributes of an {@link ExtractedType} to a specific {@link EClass}.
     * @param extractedType is the {@link ExtractedType}
     * @param eClass is the {@link EClass}.
     */
    public void addAttributes(ExtractedType extractedType, EClass eClass) {
        for (ExtractedAttribute attribute : extractedType.getAttributes()) { // for every attribute
            if (selector.allowsGenerating(attribute)) { // if should be generated:
                if (isEClass(attribute.getFullType())) { // if type is EClass:
                    EReference reference = ecoreFactory.createEReference();
                    reference.setContainment(true); // has to be contained
                    addStructuralFeature(reference, attribute, eClass); // build reference
                } else { // if it is EDataType:
                    addStructuralFeature(ecoreFactory.createEAttribute(), attribute, eClass); // build attribute
                }
            }
        }
    }

    /**
     * Adds the operations of an {@link ExtractedType} to an {@link EClass}.
     * @param type is the {@link ExtractedType}.
     * @param eClass is the {@link EClass}.
     */
    public void addOperations(ExtractedType type, EClass eClass) {
        EOperation operation;
        for (ExtractedMethod method : type.getMethods()) { // for every method
            if (selector.allowsGenerating(method)) { // if should be generated.
                operation = ecoreFactory.createEOperation(); // create object
                operation.setName(method.getName()); // set name
                addReturnType(operation, method.getReturnType(), eClass); // add return type
                addExceptions(operation, method, eClass); // add throws declarations
                addParameters(method, operation.getEParameters(), eClass); // add parameters
                eClass.getEOperations().add(operation);
            }
        }
    }

    /**
     * Adds the declared exceptions of an {@link ExtractedMethod} to an {@link EOperation}.
     */
    private void addExceptions(EOperation operation, ExtractedMethod method, EClass eClass) {
        for (ExtractedDataType exception : method.getThrowsDeclarations()) {
            typeGenerator.addException(operation, exception, eClass);
        }
    }

    /**
     * Adds the parameters of an {@link ExtractedMethod} to a specific List of {@link EParameter}s.
     */
    private void addParameters(ExtractedMethod method, List<EParameter> list, EClass eClass) {
        EParameter eParameter;
        for (ExtractedParameter parameter : method.getParameters()) { // for every parameter
            eParameter = ecoreFactory.createEParameter();
            eParameter.setName(parameter.getIdentifier()); // set identifier
            typeGenerator.addDataType(eParameter, parameter, eClass); // add type type to EParameter
            list.add(eParameter);
        }
    }

    /**
     * Adds the return type of an {@link ExtractedMethod} to an {@link EOperation}.
     */
    private void addReturnType(EOperation operation, ExtractedDataType returnType, EClass eClass) {
        if (returnType != null) { // if return type is not void
            typeGenerator.addDataType(operation, returnType, eClass); // add type to return type
        }
    }

    /**
     * Builds a structural feature from an extracted attribute and adds it to an EClass. A structural feature can be an
     * EAttribute or an EReference. If it is a reference, containment has to be set manually.
     */
    private void addStructuralFeature(EStructuralFeature feature, ExtractedAttribute attribute, EClass eClass) {
        feature.setName(attribute.getIdentifier()); // set name
        feature.setChangeable(!attribute.isFinal()); // make unchangeable if final
        typeGenerator.addDataType(feature, attribute, eClass); // add type to attribute
        eClass.getEStructuralFeatures().add(feature); // add feature to EClass
    }

    /**
     * Checks whether a specific type name is an already created EClass.
     */
    private boolean isEClass(String typeName) {
        return eClassifierMap.containsKey(typeName) && !(eClassifierMap.get(typeName) instanceof EEnum);
    }
}