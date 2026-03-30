import os
import re

root_dir = r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application"
output_file = r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\imports_result_utf8.txt"

with open(output_file, 'w', encoding='utf-8') as out_f:
    for subdir, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                filepath = os.path.join(subdir, file)
                with open(filepath, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                # Find all imports
                imports = re.findall(r'^import\s+((?:static\s+)?[\w\.]+)\s*;', content, flags=re.MULTILINE)
                
                for imp in imports:
                    class_name = imp.split('.')[-1]
                    if class_name == '*': continue
                    
                    # Check occurrences
                    occurrences = len(re.findall(r'\b' + re.escape(class_name) + r'\b', content))
                    
                    if imp.startswith("static"):
                        method_name = class_name
                        occurrences = len(re.findall(r'\b' + re.escape(method_name) + r'\b', content))
                        if occurrences <= 1:
                            out_f.write(f"[{filepath}] Unused static import: {imp}\n")
                    else:
                        if occurrences <= 1:
                            out_f.write(f"[{filepath}] Unused import: {imp}\n")
