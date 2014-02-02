package wyvern.tools.typedAST.core.declarations;

import wyvern.tools.errors.ErrorMessage;
import wyvern.tools.errors.FileLocation;
import wyvern.tools.errors.ToolError;
import wyvern.tools.typedAST.abs.Declaration;
import wyvern.tools.typedAST.core.binding.NameBinding;
import wyvern.tools.typedAST.core.binding.NameBindingImpl;
import wyvern.tools.typedAST.core.binding.ValueBinding;
import wyvern.tools.typedAST.extensions.TypeAsc;
import wyvern.tools.typedAST.interfaces.CoreAST;
import wyvern.tools.typedAST.interfaces.CoreASTVisitor;
import wyvern.tools.typedAST.interfaces.TypedAST;
import wyvern.tools.typedAST.interfaces.Value;
import wyvern.tools.types.Environment;
import wyvern.tools.types.Type;
import wyvern.tools.util.TreeWriter;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class ValDeclaration extends Declaration implements CoreAST {
	private TypeAsc asc;
	TypedAST definition;
	Type definitionType;
	NameBinding binding;

	private boolean isClass;
	public boolean isClass() {
		return isClass;
	}
	
	public ValDeclaration(String name, TypedAST definition, FileLocation location) {
		this(name, definition::typecheck, definition, location);
		this.definition=definition;
		binding = new NameBindingImpl(name, null);
		this.location = location;
	}

	public ValDeclaration(String name, Type type, TypedAST definition, FileLocation location) {
		this(name, env -> type, definition, location);
	}

	public ValDeclaration(String name, TypeAsc asc, TypedAST definition, FileLocation location) {
		this.asc = asc;
		this.definition=definition;
		binding = new NameBindingImpl(name, null);
		this.location = location;

	}


	@Override
	public void writeArgsToTree(TreeWriter writer) {
		writer.writeArgs(binding.getName(), definition);
	}

	@Override
	protected Type doTypecheck(Environment env) {
		if (getType() == null && definition == null)
			ToolError.reportError(ErrorMessage.UNEXPECTED_EMPTY_BLOCK, this);
		Type ascType = asc.getAsc(env);
		binding = new NameBindingImpl(getName(), ascType);
		if (this.definition != null)
			this.definitionType = this.definition.typecheck(env);

		if (this.definitionType != null && !this.definitionType.subtype(ascType))
			ToolError.reportError(ErrorMessage.NOT_SUBTYPE, this, this.definitionType.toString(), binding.getType().toString());

		return ascType;
	}

	@Override
	public void accept(CoreASTVisitor visitor) {
		visitor.visit(this);
	}
	
	public NameBinding getBinding() {
		return binding;
	}

	@Override
	public Type getType() {
		return binding.getType();
	}

	@Override
	public String getName() {
		return binding.getName();
	}
	
	public TypedAST getDefinition() {
		return definition;
	}

	@Override
	protected Environment doExtend(Environment old) {
		return old.extend(binding);
	}

	@Override
	public Environment extendWithValue(Environment old) {
		Environment newEnv = old.extend(new ValueBinding(binding.getName(), binding.getType()));
		return newEnv;
		//Environment newEnv = old.extend(new ValueBinding(binding.getName(), defValue));
	}

	@Override
	public void evalDecl(Environment evalEnv, Environment declEnv) {
		if (declEnv.getValue(binding.getName()) != null)
			return;
			
		Value defValue = null;
		if (definition != null)
			defValue = definition.evaluate(evalEnv);
		ValueBinding vb = (ValueBinding) declEnv.lookup(binding.getName());
		vb.setValue(defValue);
	}

	@Override
	public Map<String, TypedAST> getChildren() {
		Hashtable<String, TypedAST> children = new Hashtable<>();
		children.put("definition", definition);
		return children;
	}

	@Override
	public TypedAST cloneWithChildren(Map<String, TypedAST> nc) {
		return new ValDeclaration(getName(), nc.get("definition"), location);
	}

	private FileLocation location = FileLocation.UNKNOWN;
	public FileLocation getLocation() {
		return this.location; //TODO
	}

	@Override
	public Environment extendTypes(Environment env) {
		return env;
	}

	@Override
	public Environment extendNames(Environment env) {
		Type ascType = asc.getAsc(env);
		binding = new NameBindingImpl(binding.getName(), ascType);
		return env.extend(binding);
	}
}
