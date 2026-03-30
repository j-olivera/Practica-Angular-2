import os
import re

files_to_modify = [
    (r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application\dto\game\GameRequest.java", ["java.time.LocalDate", "java.time.LocalDateTime"]),
    (r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application\dto\game\GameResponse.java", ["java.time.LocalDateTime"]),
    (r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application\port\in\build\RetrieveBuildUseCase.java", ["com.gestor.game.core.entities.build.Build"]),
    (r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application\port\in\user\RetrieveUserUseCase.java", ["com.gestor.game.application.dto.user.UserRequest"]),
    (r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application\usecase\build\CreateBuildUseCaseImpl.java", ["com.gestor.game.application.port.out.game.GameRepositoryPort"]),
    (r"c:\Users\Juan Olivera\Desktop\Angula-2-CDI\src\main\java\com\gestor\game\application\usecase\item\RetrieveItemUseCaseImpl.java", ["java.util.Optional"])
]

for filepath, imports_to_remove in files_to_modify:
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    new_lines = []
    for line in lines:
        if line.startswith("import "):
            matched = False
            for imp in imports_to_remove:
                if line.strip() == f"import {imp};":
                    matched = True
                    break
            if matched:
                continue
        new_lines.append(line)
        
    with open(filepath, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)
    print(f"Cleaned {filepath}")
