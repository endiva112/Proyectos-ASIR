from graphviz import Digraph

flow = Digraph()

flow.node('A', 'Inicio')
flow.node('B', '¿Condición?')
flow.node('C', 'Proceso 1')
flow.node('D', 'Proceso 2')
flow.node('E', 'Fin')

flow.edges(['AB'])
flow.edge('B', 'C', label='Sí')
flow.edge('B', 'D', label='No')
flow.edges(['CE', 'DE'])

flow.render('flowchart', format='png', view=True)