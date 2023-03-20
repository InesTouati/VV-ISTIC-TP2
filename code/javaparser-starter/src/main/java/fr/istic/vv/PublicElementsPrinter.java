package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {

    private List<String> getters = new ArrayList<>();
    private String packageName, className;
    private StringBuilder varsWithoutGetter = new StringBuilder();


    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            packageName = unit.getPackageDeclaration().get().getNameAsString();
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        //System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
        //pour chaque method de la classe visitée, on sauvegarde les noms de getters existants
        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
        }
        // pour chaque field de la classe visitée, on vérifie si il est private qu'il ne possède pas de getter
        for(FieldDeclaration field : declaration.getFields()){
            field.accept(this,arg);
        }
        // Printing nested types in the top level
        /*for(BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration)
                member.accept(this, arg);
        }*/
    }

    public void visitFieldDeclaration(FieldDeclaration declaration, Void arg){
        String fieldName = declaration.getVariables().get(0).getNameAsString();
        if(declaration.getModifiers().contains(Modifier.privateModifier())){
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            if(getters.contains(getterName)){
                varsWithoutGetter.append(declaration.getVariables().get(0).getNameAsString()+" "+className+" "+packageName+"\n");
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        className = declaration.getNameAsString();
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
        visitFieldDeclaration(declaration,arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(declaration.getNameAsString().length()>2 && declaration.getNameAsString().substring(0,3).equals("get")){
            getters.add(declaration.getNameAsString());
        }
    }

    public StringBuilder getVarsWithoutGetter(){
        return varsWithoutGetter;
    }

}
