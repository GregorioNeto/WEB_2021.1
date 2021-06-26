function criar(){
    let input = document.getElementById('new_task')
    if(input.value ==""){
    }else{
        let tasks_div = document.getElementById('div_tasks')
        
        let task_div = document.createElement("div")
        task_div.className = "div_task"

        let task_label = document.createElement("label")
        let task_text = document.createTextNode(input.value)
        let task_button = document.createElement("button")
        task_button.innerText = "X"

        task_label.appendChild(task_text)
        task_div.appendChild(task_label)
        task_div.appendChild(task_button)
        tasks_div.appendChild(task_div)

        task_button.setAttribute("onclick", "deletar(event)")
        task_div.setAttribute("onclick", "concluida(event)")

        input.value = ""
    }
    
}

function deletar(Event){
    Event.target.parentNode.remove()
}

function concluida(Event){
    if(Event.target.classList.contains("div_task") ){
        if(Event.target.classList.contains("concluida")){
            Event.target.classList.remove("concluida")
        }else{
            Event.target.classList.add("concluida")
        }
    }else{
        if(Event.target.parentNode.classList.contains("concluida")){
            Event.target.parentNode.classList.remove("concluida")
        }else{
            Event.target.parentNode.classList.add("concluida")
        }
    }
    
}

document.addEventListener("keypress", function(Event){
    if(Event.keyCode == 13){
        criar()
    }
})