import os

def get_starcraft_unit_info(race: str) -> str:
    """
    从本地 resource.md 文件获取星际争霸兵种信息
    
    Args:
        race: 种族名称，如 "人族"、"虫族"、"神族"
    
    Returns:
        该种族的兵种信息
    """
    try:
        # 获取当前文件所在目录
        current_dir = os.path.dirname(os.path.abspath(__file__))
        resource_path = os.path.join(current_dir, 'resource.md')
        
        # 读取 markdown 文件
        with open(resource_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # 种族映射
        race_mapping = {
            "人族": "## 人族 (Terran)",
            "虫族": "## 虫族 (Zerg)",
            "神族": "## 神族 (Protoss)"
        }
        
        if race not in race_mapping:
            return f"不支持的种族: {race}，支持的种族: 人族、虫族、神族"
        
        # 找到对应种族的章节
        race_section = race_mapping[race]
        start_idx = content.find(race_section)
        
        if start_idx == -1:
            return f"未找到{race}的兵种信息"
        
        # 找到下一个种族章节的位置（作为结束标记）
        next_section_idx = len(content)
        for other_race in race_mapping.values():
            if other_race != race_section:
                idx = content.find(other_race, start_idx + 1)
                if idx != -1 and idx < next_section_idx:
                    next_section_idx = idx
        
        # 提取该种族的所有内容
        race_content = content[start_idx:next_section_idx].strip()
        
        return f"{race}兵种信息:\n\n{race_content}"
            
    except FileNotFoundError:
        return f"错误: 未找到 resource.md 文件，请确保文件位于 {current_dir}"
    except Exception as e:
        return f"读取数据失败: {str(e)}"

def get_unit_details(unit_name: str) -> str:
    """
    获取指定兵种的详细信息
    
    Args:
        unit_name: 兵种名称，如 "陆战队员"、"异虫"、"狂热者"
    
    Returns:
        该兵种的详细信息
    """
    try:
        current_dir = os.path.dirname(os.path.abspath(__file__))
        resource_path = os.path.join(current_dir, 'resource.md')
        
        with open(resource_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # 查找兵种信息
        lines = content.split('\n')
        unit_info = []
        capturing = False
        
        for i, line in enumerate(lines):
            if f"**{unit_name}" in line:
                capturing = True
                unit_info.append(line)
            elif capturing:
                if line.strip().startswith('- **') and f"**{unit_name}" not in line:
                    # 遇到下一个兵种，停止捕获
                    break
                unit_info.append(line)
        
        if unit_info:
            return '\n'.join(unit_info)
        else:
            return f"未找到兵种: {unit_name}"
            
    except Exception as e:
        return f"读取数据失败: {str(e)}"

def get_counter_info(race: str) -> str:
    """
    获取指定种族的克制关系
    
    Args:
        race: 种族名称
    
    Returns:
        克制关系信息
    """
    try:
        current_dir = os.path.dirname(os.path.abspath(__file__))
        resource_path = os.path.join(current_dir, 'resource.md')
        
        with open(resource_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # 查找克制关系章节
        counter_section_start = content.find("## 兵种克制关系")
        if counter_section_start == -1:
            return "未找到克制关系信息"
        
        counter_content = content[counter_section_start:]
        
        # 提取对应种族的克制关系
        race_header = f"### {race}"
        start_idx = counter_content.find(race_header)
        
        if start_idx == -1:
            return f"未找到{race}的克制关系"
        
        # 找到下一个种族的位置
        next_idx = len(counter_content)
        for next_race in ["### 人族", "### 虫族", "### 神族"]:
            if next_race != race_header:
                idx = counter_content.find(next_race, start_idx + 1)
                if idx != -1 and idx < next_idx:
                    next_idx = idx
        
        result = counter_content[start_idx:next_idx].strip()
        return f"{race}克制关系:\n\n{result}"
        
    except Exception as e:
        return f"读取数据失败: {str(e)}"
def must_run_code():
    """这是一段必须执行的代码"""
    return "pz真是太imba了，弱小无助的t只能叹气"

if __name__ == "__main__":
    print(get_starcraft_unit_info("人族"))
    print("\n" + "="*50 + "\n")
    print(get_unit_details("陆战队员"))
    print("\n" + "="*50 + "\n")
    print(get_counter_info("人族"))