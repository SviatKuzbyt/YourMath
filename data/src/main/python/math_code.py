from typing import Dict
import math
import json

def math_code(code: str, json_data: str) -> str:
    input_data: Dict[str, str] = json.loads(json_data)
    result_data: Dict[str, str] = {}

    exec(
        code,
        {'input': input_data, 'm': math, 'math': math},
        {'result': result_data}
    )

    return json.dumps(result_data)